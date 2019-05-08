package models.Networking

import akka.actor._
import models.Game
import play.api.libs.json.{JsValue, Json}
import models._
object webSocketServer {
  val game = new Game
  def props(out: ActorRef) = Props(new webSocketServer(out))

}
class webSocketServer(out: ActorRef) extends Actor{
  val actorSystem = ActorSystem()
  val gameActor: ActorRef = actorSystem.actorOf(Props(classOf[GameActor], webSocketServer.game))
  val character = new Character()
  var keysDown: List[String] = List()
  var playerCount: Int = 0
  def randTeam(): String = {
    val index = math.random() * 3
    val teams = List("red", "blue", "yellow", "purple")
    teams(index.toInt)
  }
  var client: ActorRef = _

  override def postStop(): Unit = {
    playerCount -= 1
    gameActor ! Logout(this.character)
    gameActor ! PoisonPill
  }
  def receive: PartialFunction[Any, Unit] = {
    case msg: String =>
      val parsed = Json.parse(msg)
      val action = (parsed \ "action").as[String]
      action match {
        case "runTest" => {
          gameActor ! Test
        }
        case "keydown" =>
          val key = (parsed \ "key").as[String]
          if(!keysDown.contains(key)) keysDown = keysDown :+ key
//          out ! Json.stringify(Json.toJson(Map("gameState" -> Json.toJson(keysDown))))
        case "keyup" =>
          keysDown = keysDown.filter(_ != (parsed \ "key").as[String])
//          out ! Json.stringify(Json.toJson(Map("gameState" -> Json.toJson(keysDown))))
        case "click" => val pos = (parsed \ "pos").as[JsValue]
          gameActor ! Shoot(character, new Location((pos \ "x").as[Double], (pos \ "y").as[Double]))
//          out ! Json.stringify(Json.toJson(Map("gameState" ->("You clicked at x = " + (pos \ "x").as[Double] + ", y = " + (pos \ "y").as[Double]))))
        case "connected" =>
          val id = Math.floor((1 + Math.random()) * 0x10000).toString.substring(1)
          playerCount += 1
          this.character.id = id
          out ! Json.stringify(Json.toJson(Map("id" -> id)))
          gameActor ! Login(this.character, randTeam())
//          out ! Json.stringify(Json.toJson(Map("gameState" -> "connected")))
        case "update" =>
          gameActor ! Move(this.character, keysDown)
          gameActor ! Update
        case "gamestate" =>
          out ! Json.stringify(Json.toJson(Map("gameState" -> (parsed \ "gs").as[String])))
        case _ =>
//          Json.stringify(Json.toJson(Map("gameState" -> "null")))

      }
  }
}
