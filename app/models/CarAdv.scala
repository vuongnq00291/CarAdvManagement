package models

import anorm.{SQL, ~}
import anorm.SqlParser._
import controllers.{DB, now}
import org.joda.time._
import scala.concurrent.Future

/**
  * Created by ashok on 5/23/2017.
  */

case class CarAdv(
       id      : Option[Int],
       title    : Option[String],
       fuel     : Option[String],
       price     :Option[Int],
       newCar     :Option[Int],
       mileage    :Option[Int],
       first_registration :Option[Long]
)

object CarAdv{
  val parser = {
    get[Option[Int]]("id")~get[Option[String]]("title")~get[Option[String]]("fual")~get[Option[Int]]("price")~get[Option[Int]]("newone")~get[Option[Int]]("mileage")~get[Option[Long]]("first_registration")
  }.map{
    case id~title~fual~price~newone~mileage~frDate => CarAdv(id,title,fual,price,newone,mileage,frDate)
  }
  def g(id:Int) = Future {
    val query = SQL("SELECT * FROM CarAdv where id = {id}")
    DB.withConnection { implicit connection =>
      query.on(
        'id -> id
      ).as(parser.singleOpt)
    }
  }

  def gets() = Future{
    val query = SQL("SELECT * FROM CarAdv ORDER BY id DESC")
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
        'frdate ->  adv.first_registration
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
        'frdate ->  adv.first_registration,
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