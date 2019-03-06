package database

import java.sql._

import play.api.libs.json._

object userDataHandling{
  val user = "root"
  val pass = "pass123"
  var connection: Connection = _
  var connected: Boolean = false
  def connect(): Unit = {
    val url = "jdbc:mysql://localhost:3306/grandecafeLatte?characterEncoding=UTF-8"
//    Class.forName("com.mysql.jdbc.Driver").newInstance()
    println("Connecting to Database")
    connection = DriverManager.getConnection(url, user, pass)
    println("Connected to Database")
    connected = true
  }
  def createDB(): Unit = {
    val url = "jdbc:mysql://localhost:3306?characterEncoding=UTF-8"
    Class.forName("com.mysql.jdbc.Driver").newInstance()
    connection = DriverManager.getConnection(url, user, pass)
    val st = connection.createStatement()
    val sql = "CREATE DATABASE IF NOT EXISTS grandeCafeLatte;"
    st.executeUpdate(sql)
    println("Database Created")
    connect()
  }
  def createTable(): Unit = {
    if (connected) {
      val st = connection.createStatement()
      val sql = "CREATE TABLE IF NOT EXISTS userlist (" +
        "id INT," +
        "username VARCHAR(20)," +
        "profession VARCHAR(7));"
      st.executeUpdate(sql)
      println("Table Created")
    }
    else{
      connect()
      createTable()
    }
  }
  def addUser(username: String, profession: String): Int = {
    if(connected) {
      val id = getNewId()
      val st = connection.createStatement()
      val addUserSQL = "INSERT INTO userlist VALUES (" + id + ", '" + username + "', '" + profession +"');"
      st.executeUpdate(addUserSQL)
      println( profession + " " + username + " added with ID: " + id)
      id
    }
    else{
      connect()
      addUser(username, profession)
    }
  }
  def getNewId(): Int = {
    var id: Int = 0
      val st = connection.createStatement()
      val getIdSQL = "SELECT MAX(id) FROM userlist"
      val result = st.executeQuery(getIdSQL)
      while (result.next()) {
        id = result.getInt(1)
        id += 1
      }
      id
  }
  def getUserById(id: Int): JsObject = {
    if(connected){
      var username = ""
      var profession = "DNE"
      val st = connection.createStatement()
      val sql = "SELECT id, username, profession FROM userlist WHERE id =" + id
      val result = st.executeQuery(sql)
      while(result.next()) {
        username = result.getString("username")
        profession = result.getString("profession")
      }
      Json.obj("id" -> id, "username" -> username, "profession" -> profession)
    }
    else{
      connect()
      getUserById(id)
    }
  }

  def getUserByName(username: String): JsObject = {
    if(connected){
      var id = 0
      var profession = "DNE"
      var users = Json.obj()
      val st = connection.createStatement()
      val sql = "SELECT id, username, profession FROM userlist WHERE username = '" + username + "';"
      val result = st.executeQuery(sql)
      while(result.next()) {
        id = result.getInt("id")
        profession = result.getString("profession")
        users += (id.toString -> Json.obj("username" -> username, "profession" -> profession))
      }
      users
    }
    else{
      connect()
      getUserByName(username)
    }
  }
  def getAllUsers(): JsObject = {
    if(connected){
      val st = connection.createStatement()
      val sql = "SELECT id, username, profession FROM userlist;"
      val result = st.executeQuery(sql)
      var UserMap: Map[String, JsObject] = Map()
      while(result.next()){
        val id = result.getInt("id")
        val username = result.getString("username")
        val profession = result.getString("profession")
        UserMap += (id.toString -> Json.obj("username" -> username, "profession" -> profession))
      }
      JsObject(UserMap)
    }
    else{
      connect()
      getAllUsers()
    }
  }
  def close(): Unit ={
    if(connected){
      connection.close()
      connected = false
    }
  }

  def main(args: scala.Array[String]): Unit = {
    createDB()
    createTable()
  }
}