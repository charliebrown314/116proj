package models.Networking

import akka.actor.ActorRef
import models._

case class Move(user: Character,keysDown: List[String])
case class Shoot(user: Character, mousePos: Location)
case object Update
case class Login(user: Character, team: String)
case class Logout(user: Character)