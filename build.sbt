name := "CarAdvManagement"

version := "1.0"

lazy val `caradvmanagement` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( evolutions, jdbc , cache , ws   ,
  specs2 % Test ,
  "joda-time" % "joda-time" % "2.3",
  "org.xerial" % "sqlite-jdbc" % "3.7.15-M1",
  "com.typesafe.play" %% "anorm" % "2.4.0",
  "io.spray" %%  "spray-json" % "1.2.6"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  