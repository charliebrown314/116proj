package models

import akka.actor.ActorRef

class Character(var id: String = "0",var team: String = "grey", var location: Location = new Location(0,0)){
  var HP: Double = 100.0
}

