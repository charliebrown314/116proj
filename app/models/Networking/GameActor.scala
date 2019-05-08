package models.Networking

import models._
import play.api.libs.json.Json

class GameActor(game: Game) extends Actor{
  override def receive: Receive = {
    case Update => sender() ! Json.stringify(Json.toJson(Map("action" -> "gamestate","gs" -> game.update)))
    case move: Move => game.movePlayer(move.user, move.keysDown)
    case login: Login => game.addPlayer(login.user, login.team)
    case logout: Logout => game.removePlayer(logout.user)
    case shoot: Shoot => game.shoot(shoot.user, shoot.mousePos)
  }
}
