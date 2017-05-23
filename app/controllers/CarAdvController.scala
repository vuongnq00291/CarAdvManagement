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

  implicit val colorFormat = jsonFormat7(CarAdv.apply)
  val parser = {
    int("id")~str("title")~str("fual")~int("price")~int("newone")~int("mileage")
  }.map{
    case id~title~fual~price~newone~mileage => CarAdv(id,title,fual,price,newone,mileage,now.getMillis)
  }

  def get(id:Int)= Action.async{
    CarAdv.get(id).map{
      case Some(item:CarAdv) => Ok(Json.toJson(item))
      case _ => Ok("nonono")
    }
  }

  def gets()= Action.async{
    CarAdv.gets.map{
      case items:List[CarAdv] => Ok(Json.toJson(items))
      case _ =>  NoContent
    }
  }

  def post() = Action.async(parse.tolerantText) { implicit request =>
    val s = request.body.toString
    val jsonAst = s.parseJson
    val adv = jsonAst.convertTo[CarAdv]
    CarAdv.create(adv).map{
      case Some(id:Long) => Created(Json.obj("created" -> id))
      case _ => InternalServerError(Json.obj("created" -> false))
    }
  }

  def put() = Action.async(parse.tolerantText) { implicit request =>
    val s = request.body.toString
    val jsonAst = s.parseJson
    val adv = jsonAst.convertTo[CarAdv]
    CarAdv.update(adv).map{
      case Some(id:Long) => Created(Json.obj("updated" -> id))
      case _ => InternalServerError(Json.obj("fail" -> false))
    }
  }
}
