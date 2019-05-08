package models

class Projectile(val team: String, var location: Location, var endpt: Location, var height: Double, var angle: Double = 0) {
  def collide(game: Game): Unit ={
    game.projectiles = game.projectiles.filter(_ != this)
  }
}
