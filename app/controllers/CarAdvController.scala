package controllers

import com.fasterxml.jackson.databind.ObjectMapper
import models.CarAdv
import play.api.Play.current
import play.api.db._
import play.api.libs.json.Json
import play.api.mvc._
import anorm.{~, _}
import spray.json._
import DefaultJsonProtocol._
import akka.util.Helpers.Requiring

import scala.concurrent.Future


/**
  * Created by ashok on 5/23/2017.
  */
object CarAdvController extends  Controller {

  implicit val colorFormat = jsonFormat7(CarAdv.apply)


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
  def validate(adv:CarAdv)  = Future{

  }

  def put(id:Int) = Action.async(parse.tolerantText) { implicit request =>
    val s = request.body.toString
    val jsonAst = s.parseJson
    val adv = jsonAst.convertTo[CarAdv]
    CarAdv.update(id,adv).map{
      case Some(id:Long) => Created(Json.obj("updated" -> id))
      case _ => InternalServerError(Json.obj("fail" -> false))
    }
  }

  def delete(id:Int)= Action.async{
    CarAdv.delete(id).map{
      case rows:Int if rows > 0 => Accepted(Json.obj("deleted" -> true))
      case _ => InternalServerError(Json.obj("deleted" -> false))
    }
  }
}
