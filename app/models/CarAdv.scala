package models

import anorm.{SQL, ~}
import anorm.SqlParser._
import controllers.{DB, now}

import scala.concurrent.Future

/**
  * Created by ashok on 5/23/2017.
  */

case class CarAdv(
       id      : Int,
       title    : String,
       fuel     : String,
       price     :Int,
       newCar     :Int,
       mileage    :Int,
       first_registration :Long
)

object CarAdv{
  val parser = {
    int("id")~str("title")~str("fual")~int("price")~int("newone")~int("mileage")
  }.map{
    case id~title~fual~price~newone~mileage => CarAdv(id,title,fual,price,newone,mileage,now.getMillis)
  }
  def get(id:Int) = Future {
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
  }

  def gets = Future{
    val query = SQL("SELECT * FROM CarAdv")
    DB.withConnection { implicit connection =>
      query.on().as(parser*)
    }
  }

  def create(adv:CarAdv) = Future {
    val query = SQL("INSERT INTO CarAdv (title,fual,price,newone,mileage,first_registration)  VALUES ({title} ,{fuel}, {price},{newone}, {miles},{frdate})")
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
  }

  def update(id:Int,adv:CarAdv) = Future {
    val query = SQL("UPDATE CarAdv SET title={title},fual={fual}," +
      "price={price},newone={newone},mileage={miles},first_registration={frdate} where id = {id}")
    DB.withConnection {con =>
      query.on(
        'title -> adv.title,
        'fual -> adv.fuel,
        'price -> adv.price,
        'newone -> adv.newCar,
        'miles -> adv.mileage,
        'frdate ->  now.toString,
        'id ->  id
      ).executeInsert()
    }
  }

  def delete(id:Int) = Future {
    DB.withConnection { implicit connection =>
      SQL(
        """
          DELETE
          FROM CarAdv
          WHERE id = {id};
        """
      ).on(
        'id -> id
      ).executeUpdate()
    }
  }

}