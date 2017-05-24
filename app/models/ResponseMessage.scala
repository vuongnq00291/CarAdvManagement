package models
import java.util.ArrayList
/**
  * Created by ashok on 5/24/2017.
  */
case class ResponseMessage(error: Option[Seq[String]],adv: Option[CarAdv])
