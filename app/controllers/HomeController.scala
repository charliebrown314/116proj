package controllers

import javax.inject._
import play.api._
import play.api.libs.json.{JsObject, Json}
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  //Html Rendering
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  def instructions() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.Instructions())
  }
  def dbDemo() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.databaseDemo())
  }
  //Database Routing
  def allUsers() = Action{ request =>
    val userlist = database.userDataHandling.getAllUsers()
    if(userlist == Json.obj()){
      Ok("No Users in Database.")
    }
    else{
      Ok(userlist)
    }
  }
  def userById(id: Int) = Action{ request =>
    val user = database.userDataHandling.getUserById(id)
    if((user \ "profession").as[String] == "DNE"){
      Ok("User Id not Found.")
    }
    else{
      Ok(user)
    }
  }
  def userByName(name: String) = Action{request =>
    val userlist = database.userDataHandling.getUserByName(name)
    if(userlist == Json.obj()){
      Ok("Username not Found.")
    }
    else{
      Ok(userlist)
    }
  }
  def addUser(name: String, profession: String) = Action{request =>
    val id = database.userDataHandling.addUser(name, profession)
    Ok(profession + " " + name + " added to Database with ID: " + id)
  }
  }
