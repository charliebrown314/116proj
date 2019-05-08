package models

class Location(var x: Double, var y: Double) {
  def distance(pt2: Location): Double ={
    math.sqrt(math.pow(pt2.x - this.x, 2) + math.pow(pt2.y - this.y, 2))
  }
}