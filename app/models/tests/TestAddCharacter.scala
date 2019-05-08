package models.tests
import models.Game
import org.scalatest._
class TestAddCharacter extends FunSuite{
  test("Test Add Player") {
    val character1 = new models.Character()
    val character2 = new models.Character()
    val character3 = new models.Character()
    val character4 = new models.Character()
    val Game = new Game()
    Game.addPlayer(character1, "red")
    assert(character1.team == "red")
    Game.addPlayer(character2, "yellow")
    assert(character2.team == "yellow")
    Game.addPlayer(character3, "blue")
    assert(character3.team == "blue")
    Game.addPlayer(character4, "purple")
    assert(character4.team == "purple")
  }
}
