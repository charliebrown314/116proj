/*package tests

import models._
import org.scalatest.FunSuite

class TestDamage extends FunSuite {
  test("Test Damage") {
    val pin: String = "Pin"
    val fireball: String = "Fireball"
    val smite: String = "Smite"
    val example1: Character = new Warrior("Bob")
    val example2: Character = new Ranger("Kevin")
    val example3: Character = new Wizard("Chen")
    val example4: Character = new Warrior("Bob")
    val example5: Character = new Ranger("Kevin")
    val example6: Character = new Wizard("Chen")
    val example7: Character = new Warrior("Bob")
    val example8: Character = new Ranger("Kevin")
    val example9: Character = new Wizard("Chen")
    Game.Damage(example1, smite)
    Game.Damage(example2, smite)
    Game.Damage(example3, smite)
    Game.Damage(example4, pin)
    Game.Damage(example5, pin)
    Game.Damage(example6, pin)
    Game.Damage(example7, fireball)
    Game.Damage(example8, fireball)
    Game.Damage(example9, fireball)
    assert(example1.HP == 180)
    assert(example4.HP == 170)
    assert(example7.HP == 160)
    assert(example2.HP == 130)
    assert(example5.HP == 120)
    assert(example8.HP == 110)
    assert(example3.HP == 80)
    assert(example6.HP == 70)
    assert(example9.HP == 60)
  }
}
*/