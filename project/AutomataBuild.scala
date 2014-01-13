import sbt._
import Keys._

object WorkmapBuild extends Build {
  // Akka
  lazy val akkaActor = "com.typesafe.akka" %% "akka-actor" % "2.2.3"
  lazy val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % "2.2.3"

  // Utilities
  lazy val config = "com.typesafe" % "config" % "1.0.2"
  lazy val specs2 = "org.specs2" %% "specs2" % "2.2.3" % "test"

  // Logging
  lazy val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.0.13"
  lazy val logbackCore = "ch.qos.logback" % "logback-core" % "1.0.13"
  lazy val slf4jApi = "org.slf4j" % "slf4j-api" % "1.7.5"

  // Spray
  lazy val sprayCan = "io.spray" % "spray-can" % "1.2.0"
  lazy val sprayJson = "io.spray" % "spray-json_2.10" % "1.2.5"
  lazy val sprayRouting = "io.spray" % "spray-routing" % "1.2.0"
  lazy val sprayTestkit = "io.spray" % "spray-testkit" % "1.2.0"

  lazy val lsgu_automata = Project(id = "lsgu-automata", base = file("./"), settings = Project.defaultSettings ++ Seq(
    scalaVersion := "2.10.3",
    sbtVersion := "0.13.1",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    resolvers ++= Seq(
      "spray repo" at "http://repo.spray.io",
      "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"),
    libraryDependencies ++= Seq(
      akkaActor,
      akkaSlf4j,
      config,
      specs2,
      logbackClassic,
      logbackCore,
      slf4jApi,
      sprayCan,
      sprayJson,
      sprayRouting,
      sprayTestkit
    )))
}
