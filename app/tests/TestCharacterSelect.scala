/*package tests

import models._
import models.Game._
import org.scalatest.FunSuite

class TestCharacterSelect extends FunSuite {
  test("Test Character Select") {
    val name1: String = "Bob"
    val name2: String = "Kevin"
    val name3: String = "Tyrone"
    val name4: String = "Chen"
    val warrior: String = "Warrior"
    val wizard: String = "Wizard"
    val ranger: String = "Ranger"
    val example1: Character = new Warrior(name3)
    val example2: Character = new Ranger(name2)
    val example3: Character = new Wizard(name1)
    assert(CharacterSelect(name1, warrior).HP == example1.HP)
    assert(CharacterSelect(name1, warrior).HP == 200)
    assert(CharacterSelect(name2, wizard).HP == example3.HP)
    assert(CharacterSelect(name2, wizard).HP == 100)
    assert(CharacterSelect(name3, ranger).HP == example2.HP)
    assert(CharacterSelect(name3, ranger).HP == 150)
    assert(CharacterSelect(name1, warrior).name1 == "Bob")
    assert(CharacterSelect(name2, ranger).name1 == "Kevin")
    assert(CharacterSelect(name3, ranger).name1 == "Tyrone")
    assert(CharacterSelect(name4, ranger).name1 == "Chen")
  }
}
*/