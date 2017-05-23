/**
  * Created by vuong on 24/5/2017.
  */
package object models {
  implicit def current = play.api.Play.current
  def DB = play.api.db.DB
  implicit var connection = DB.getConnection()
  implicit def global = scala.concurrent.ExecutionContext.Implicits.global
}
