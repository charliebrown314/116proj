/*package tests

import models._
import org.scalatest.FunSuite

class TestMovement extends FunSuite {
  test("Test Movement") {
    val move1: Movement = new Movement(102, 62, "W")
    val move2: Movement = new Movement(124, 14, "A")
    val move3: Movement = new Movement(8, 8, "S")
    val move4: Movement = new Movement(0, 0, "D")
    val location1: Location = new Location(102, 60)
    val location2: Location = new Location(122, 14)
    val location3: Location = new Location(8, 10)
    val location4: Location = new Location(2, 0)
    assert(Game.Move(move1).y == location1.y)
    assert(Game.Move(move1).x == location1.x)
    assert(Game.Move(move2).y == location2.y)
    assert(Game.Move(move2).x == location2.x)
    assert(Game.Move(move3).y == location3.y)
    assert(Game.Move(move3).x == location3.x)
    assert(Game.Move(move4).y == location4.y)
    assert(Game.Move(move4).x == location4.x)
  }
}
*/