package controllers

import java.util

import com.fasterxml.jackson.databind.ObjectMapper
import models.{CarAdv, ResponseMessage}
import play.api.Play.current
import play.api.db._
import play.api.libs.json.Json
import play.api.mvc._
import anorm.{~, _}
import spray.json._
import DefaultJsonProtocol._
import java.util.ArrayList
import collection.JavaConversions._
import akka.util.Helpers.Requiring

import scala.concurrent.Future


/**
  * Created by ashok on 5/23/2017.
  */
object CarAdvController extends  Controller {

  implicit val colorFormat = jsonFormat7(CarAdv.apply)


  def get(id:Int)= Action.async{
    CarAdv.g(id).map{
      case Some(item:CarAdv) => Ok(Json.toJson(ResponseMessage(None,Some(item))))
      case _ => {
        val errors = List("No record found.");
        Ok(Json.toJson(ResponseMessage(Some(errors),None)))
      }
    }
  }

  def gets()= Action.async{
    CarAdv.gets.map{
      case items:List[CarAdv] => Ok(Json.toJson(items))
      case _ =>  {
        val errors = List("No record found.");
        Ok(Json.toJson(ResponseMessage(Some(errors),None)))
      }
    }
  }

  def post() = Action.async(parse.tolerantText) { implicit request =>
    val s = request.body.toString
    val jsonAst = s.parseJson
    val adv = jsonAst.convertTo[CarAdv]
    val errors = validate(adv)
    if(errors.size>0){
      Future{
        val res = ResponseMessage(Some(errors),None)
        BadRequest(Json.toJson(res))
      }
    }else{
      CarAdv.create(adv).map{
        case Some(id:Long) => Created(Json.obj("created" -> id))
        case _ => InternalServerError(Json.obj("created" -> false))
      }
    }

  }

  def put(id:Int) = Action.async(parse.tolerantText) { implicit request =>
    val s = request.body.toString
    val jsonAst = s.parseJson
    val adv = jsonAst.convertTo[CarAdv]
    val errors = validate(adv)
    if(errors.size>0){
      Future{
        val res = ResponseMessage(Some(errors),None)
        BadRequest(Json.toJson(res))
      }
    }else{
      CarAdv.update(id,adv).map{
        case Some(id:Long) => Created(Json.obj("updated" -> id))
        case _ => InternalServerError(Json.obj("fail" -> false))
      }
    }

  }

  def delete(id:Int)= Action.async{
    CarAdv.delete(id).map{
      case rows:Int if rows > 0 => Accepted(Json.obj("deleted" -> true))
      case _ => InternalServerError(Json.obj("deleted" -> false))
    }
  }

  private def validate(adv:CarAdv):  Seq[String]= {
    var errors = new ArrayList[String]
    if(adv.id==None){
      errors.add("id is required")
    }
    if(adv.title==None){
      errors.add("title is required")
    }
    if(adv.price==None){
      errors.add("price is required")
    }
    if(adv.newCar==None){
      errors.add("newCar is required")
    }
    if(adv.newCar==Some(1)){
      if(adv.mileage==None){
        errors.add("mileage is required")
      }
      if(adv.first_registration==None){
        errors.add("first registration is required")
      }
    }
    return errors
  }
}
