package models

import java.awt.geom.Line2D

import play.api.libs.json.Json

class Game {
  var gameOver: (Boolean, String, Long) = (false, "grey", 0)
  var players: List[Character] = List()
  var CPs: List[CP] = List(
    new CP(new Location(1180,20), "red"),
    new CP(new Location(20,20), "yellow"),
    new CP(new Location(20,555), "blue"),
    new CP(new Location(1180,555), "purple"),
    new CP(new Location(300,125), "grey"),
    new CP(new Location(900,125), "grey"),
    new CP(new Location(300,450), "grey"),
    new CP(new Location(900,450), "grey"),
    new CP(new Location(600,287.5), "grey")
  )
  var walls: List[Wall] = List(
    new Wall(new Location(1190, 0), 575, 10),
    new Wall(new Location(0,0),575, 10),
    new Wall(new Location(0,0), 10, 1200),
    new Wall(new Location(0, 565), 10, 1200),

    new Wall(new Location(280, 105), 100, 10),
    new Wall(new Location(290,105), 10, 100),

    new Wall(new Location(910,105), 100, 10),
    new Wall(new Location(820, 105), 10, 100),

    new Wall(new Location(280, 370), 100, 10),
    new Wall(new Location(280,460), 10, 100),

    new Wall(new Location(910, 370), 100, 10),
    new Wall(new Location(810,460), 10, 100),

    new Wall(new Location(475, 210), 150, 10),
    new Wall(new Location(525,170), 10, 150),
    new Wall(new Location(725,210), 150, 10),
    new Wall(new Location(525, 395), 10, 150),
  )
  var projectiles: List[Projectile] = List()
  def addPlayer(character: Character, team: String): Unit = {
    character.team = team
    if(!players.contains(character)) {
      respawn(character)
      players = players :+ character
    }
  }
  def removePlayer(character: Character): Unit = {
    players = players.filter(_ != character)
  }
  def respawn(player: Character): Unit ={
    val pts: List[CP] = CPs.filter(_.team == player.team)
    if (pts.nonEmpty) {
      val spawn = pts((math.random() * pts.indices.length).toInt)
      player.location = new Location(spawn.location.x, spawn.location.y)
      player.HP = 100
    }
    else gameOver = (true, player.team, System.nanoTime())
  }
  def newLoc(player: Character, keysPressed: List[String]): Location ={
    var x = player.location.x
    var y = player.location.y
    if (keysPressed.contains("KeyW")) y -= 2
    if (keysPressed.contains("KeyA")) x -= 2
    if (keysPressed.contains("KeyS")) y += 2
    if (keysPressed.contains("KeyD")) x += 2
    new Location(x,y)
  }
  def movePlayer(player: Character, keysPressed: List[String]): Unit = {
    if(checkForWall(player, newLoc(player,keysPressed))){
      player.location = newLoc(player, keysPressed)
    }
  }
  def checkForWall(char: Character, potentialLocation: Location): Boolean ={
    var bool = true
    walls.foreach(wall =>
        if(new Line2D.Double(char.location.x,char.location.y,potentialLocation.x,potentialLocation.y).intersects(wall.location.x,wall.location.y,wall.width,wall.length)) {
          bool = false
        }
      )
    bool
  }
  def shoot(player: Character, endpt: Location): Unit ={
    val angle = Math.atan2(endpt.y - player.location.y, endpt.x - player.location.x)
    val projectile = new Projectile(player.team, new Location(player.location.x + Math.cos(angle) * 9, player.location.y + Math.sin(angle) * 9), endpt, 3, angle)
    projectiles = projectiles :+ projectile
  }
  def checkForHit(): Unit ={
    val projDistance = (char: Character, proj: Projectile) => char.location.distance(proj.location)
    if(players.nonEmpty) {
      for (proj <- projectiles) {
        val player = players.minBy(char => projDistance(char, proj))
        if (projDistance(player, proj) <= 5 && player.team != proj.team) {
          player.HP -= 15
          proj.collide(this)
        }
      }
    }
  }
  def checkHP(): Unit = {
    players.foreach(player =>
      if(player.HP <= 0.0) respawn(player)
    )
  }
  def projLoc(projectile: Projectile): Location = {
        val location = projectile.location
        val angle = projectile.angle
        location.x += Math.cos(angle) * 3
        location.y += Math.sin(angle) * 3
        location
      }
  def moveProjectiles(): Unit = {
    projectiles.foreach(projectile => {
    if(projectile.height <= 0) projectile.collide(this)
    else if(checkForWallProj(projectile, projLoc(projectile))) {
        projectile.location = projLoc(projectile)
        projectile.height -= .1
      }
    else projectile.collide(this)
    })
    }

  def checkForWallProj(projectile: Projectile, potentialLocation: Location): Boolean ={
    var bool = true
    walls.foreach(wall =>
      if(new Line2D.Double(projectile.location.x,projectile.location.y,potentialLocation.x,potentialLocation.y).intersects(wall.location.x,wall.location.y,wall.width,wall.length)) {
        bool = false
      }
    )
    bool
  }
  def capturePoints(time: Long): Unit={
    val cpDistance = (char: Character, cp: CP) => char.location.distance(cp.location)
    if(players.nonEmpty) {
      for (cp <- CPs) {
        val player = players.minBy(char => cpDistance(char, cp))
        if (cpDistance(player, cp) <= 7 && player.team != cp.team) {
          if(!cp.occupied){
            cp.occupied = true
            cp.lastUpdateTime = time
          }
          else{
            val timeElapsed = (time - cp.lastUpdateTime) / 1000000000
            if(cp.team == "grey" && timeElapsed > 2 && timeElapsed < 4){
              cp.team = player.team
              cp.occupied = false
            }
            else if(cp.team != "grey" && timeElapsed > 2 && timeElapsed < 4){
              cp.team = "grey"
              cp.lastUpdateTime = time
            }
          }
        }
        else cp.occupied = false
      }
    }
  }
  def resetGame(): Unit ={
    CPs = List(
      new CP(new Location(1180,20), "red"),
      new CP(new Location(20,20), "yellow"),
      new CP(new Location(20,555), "blue"),
      new CP(new Location(1180,555), "purple"),
      new CP(new Location(300,125), "grey"),
      new CP(new Location(900,125), "grey"),
      new CP(new Location(300,450), "grey"),
      new CP(new Location(900,450), "grey"),
      new CP(new Location(600,287.5), "grey")
    )
    players.foreach(player => respawn(player))
    projectiles.foreach(proj => proj.collide(this))
  }
  def update(): String ={
    if(gameOver._1){
      val timeRemaining = 5 -((System.nanoTime - gameOver._3) / 1000000000)
      if (timeRemaining <= 0) {
        gameOver = (false, "grey", 0)
        resetGame()
        Json.stringify(Json.toJson(Map("losingTeam" -> gameOver._2, "restartTimer" -> "0")))
      }
      else Json.stringify(Json.toJson(Map("losingTeam" -> gameOver._2, "restartTimer" -> timeRemaining.toString)))
    }
    else {
      checkForHit()
      moveProjectiles()
      checkHP()
      capturePoints(System.nanoTime())
      Json.stringify(Json.toJson(
        Map("players" -> Json.toJson(this.players.map(x => Json.toJson(
          Map(
            "x" -> Json.toJson(x.location.x),
            "y" -> Json.toJson(x.location.y),
            "team" -> Json.toJson(x.team),
            "HP" -> Json.toJson(x.HP),
            "id" -> Json.toJson(x.id)
          )
        ))
        ), "CPs" -> Json.toJson(this.CPs.map(x =>
          Map(
            "x" -> Json.toJson(x.location.x - 25 / 2),
            "y" -> Json.toJson(x.location.y - 25 / 2),
            "team" -> Json.toJson(x.team)
          )
        )), "projectiles" -> Json.toJson(this.projectiles.map(x =>
          Map(
            "x" -> Json.toJson(x.location.x),
            "y" -> Json.toJson(x.location.y),
          )
        )
        ),
          "walls" -> Json.toJson(this.walls.map(wall =>
            Map(
              "x" -> Json.toJson(wall.location.x),
              "y" -> Json.toJson(wall.location.y),
              "h" -> Json.toJson(wall.length),
              "w" -> Json.toJson(wall.width)
            )
          ))
        )))
    }
  }
}
