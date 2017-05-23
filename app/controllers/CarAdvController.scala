package controllers

import com.fasterxml.jackson.databind.ObjectMapper
import models.CarAdv
import play.api.Play.current
import play.api.db._
import play.api.libs.json.Json
import play.api.mvc._
import anorm.{~, _}
import anorm.SqlParser._
import spray.json._
import DefaultJsonProtocol._
import scala.concurrent.Future


/**
  * Created by ashok on 5/23/2017.
  */
object CarAdvController extends  Controller {
  def hello(name: String) = Action {
    Ok("Hello " + name)
  }
  implicit val colorFormat = jsonFormat7(CarAdv.apply)
  val parser = {
    int("id")~str("title")~str("fual")~int("price")~int("newone")~int("mileage")
  }.map{
    case id~title~fual~price~newone~mileage => CarAdv(id,title,fual,price,newone,mileage,now.getMillis)
  }
  def post() = Action(parse.tolerantText) { implicit request =>

    val mapper = new ObjectMapper()
    val s = request.body.toString
    val jsonAst = s.parseJson
    print(s)
    val adv = jsonAst.convertTo[CarAdv]
    val query = SQL("INSERT INTO CarAdv (title,fual,price,newone,mileage,first_registration)  VALUES ({title} ,{fuel}, {price},{newone}, {miles},{frdate})");

    DB.withConnection {con =>
      query.on(
        'title -> adv.title,
        'fuel -> adv.fuel,
        'price -> adv.price,
        'newone -> adv.newCar,
        'miles -> adv.mileage,
        'frdate ->  now.toString
      ).executeInsert()
    }
    Ok("good")
  }
  def get(id:Int)= Action.async{

    Future{
      val parser = {
        int("id")~str("title")~str("fual")~int("price")~int("newone")~int("mileage")
      }.map{
        case id~title~fual~price~newone~mileage => CarAdv(id,title,fual,price,newone,mileage,now.getMillis)
      }
      val query = SQL("SELECT * FROM CarAdv where id = {id}")
      DB.withConnection { implicit connection =>
        query.on(
          'id -> id
        ).as(parser.singleOpt)
      }
    }.map{
      case Some(item:CarAdv) => Ok(Json.toJson(item))
      case _ => Ok("nonono")
    }
  }

  def gets()= Action.async{

    Future{

      val query = SQL("SELECT * FROM CarAdv")
      DB.withConnection { implicit connection =>
        query.on().as(parser*)
      }
    }.map{
      case items:List[CarAdv] => Ok(Json.toJson(items))
      case _ => Ok("nonono")
    }
  }
}
