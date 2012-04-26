package models.scala.mongodb

case class ScalaMongoDB(name: String)

object ScalaMongoDB {

  import com.mongodb._

  def testMongoDB() {
    // MongoDB Java API in Scala benutzen
    new Mongo()

    // Besser: MongoDB Scala Wrapper "Casbah" (oder anderen!) verwenden => hat viele Vorteile
    // siehe z.B. http://api.mongodb.org/scala/casbah/current/tutorial.html
  }

}
