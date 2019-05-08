package models.Networking
import akka.actor._
import models._
import play.api.libs.json.Json
case class Test(playerCount: Int)
class GameActor(game: Game) extends Actor{
  override def receive: Receive = {
    case Update => sender() ! Json.stringify(Json.toJson(Map("action" -> "gamestate","gs" -> game.update)))
    case move: Move => game.movePlayer(move.user, move.keysDown)
    case login: Login => game.addPlayer(login.user, login.team)
    case logout: Logout => game.removePlayer(logout.user)
    case shoot: Shoot => game.shoot(shoot.user, shoot.mousePos)
    case test: Test => if (game.players.length == test.playerCount) {
      val testString = "Test Passed: " + test.playerCount.toString() + " = " + game.players.length.toString()
      sender() ! Json.stringify(Json.toJson(Map("test" -> testString)))
    }
      else{
      val testString = "Test Failed: " + test.playerCount.toString() + " != " + game.players.length.toString()
      sender() ! Json.stringify(Json.toJson(Map("test" -> testString)))
    }
  }
}
