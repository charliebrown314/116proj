package models.tests
import models.Game
import org.scalatest._
class TestCheckHP extends FunSuite {
  test("Test Check HP") {
    val character = new models.Character()
    val character1 = new models.Character()
    val character2 = new models.Character()
    val game = new Game
    game.addPlayer(character, "red")
    character.HP = 0
    game.checkHP()
    assert(character.HP == 100)
    character1.HP = 10
    assert(character1.HP == 10)
    character2.HP = 50
    assert(character2.HP == 50)

  }
}
