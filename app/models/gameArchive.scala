/*
package models

object gameArchive {
  val Fireball: Double = 40.0
  val Smite: Double = 20.0
  val Pin: Double = 30.0
  def CharacterSelect(name: String, myClass: String): Character = {
    var character: Character = new Wizard(name)
    if (myClass == "Wizard") {
      character = new Wizard(name)
    }
    if (myClass == "Warrior") {
      character = new Warrior(name)
    }
    if (myClass == "Ranger") {
      character = new Ranger(name)
    }
    character
  }

  def Move(movement: Movement): Location = {
    if (movement.string == "W") {
      movement.y = movement.y - 2
    }
    if (movement.string == "A") {
      movement.x = movement.x - 1
    }
    if (movement.string == "S") {
      movement.y = movement.y + 2
    }
    if (movement.string == "D") {
      movement.x = movement.x + 1
    }
    val location = new Location(movement.x, movement.y)
    location
  }
  def Damage(myCharacter: Character, sustainDamage: String): Unit = {
    if (sustainDamage == "Fireball") {
      myCharacter.HP -= Fireball
    }
    if (sustainDamage == "Smite") {
      myCharacter.HP -= Smite
    }
    if (sustainDamage == "Pin") {
      myCharacter.HP -= Pin
    }
  }
}
*/
