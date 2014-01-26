package org.lsug.restlife

import spray.json._
import spray.routing.{RoutingSettings, Directives, HttpServiceActor}
import akka.actor.{ActorRefFactory, Props, ActorSystem, Actor}
import scala.annotation.tailrec
import akka.io.IO
import spray.can.Http
import org.slf4j.{Logger, LoggerFactory}
import java.util.Date

case class BoardRequest(board: LifeSym.Board, steps: Int)

object BoardJsonProtocol extends DefaultJsonProtocol {
  implicit val boardRequestFormat = jsonFormat2(BoardRequest)
}

trait Routes extends Directives {
  import BoardJsonProtocol._
  import spray.httpx.SprayJsonSupport._
  def logger: Logger
  implicit def actorRefFactory: ActorRefFactory

  def route(implicit rs: RoutingSettings) =
    path("board") {
      post {
        entity(as[BoardRequest]) {
          case req @ BoardRequest(board, steps) => complete {
            logger.info(s"Serving request: $req")
            LifeSym.sym(board, steps)
          }
        }
      }
    } ~
    pathPrefix("web") {
      get {
//        complete { "OK" }
        getFromResourceDirectory("ui")
      }
    }
}

class Router extends HttpServiceActor with Routes {
  val logger = LoggerFactory.getLogger(this.getClass())
  def receive: Actor.Receive = runRoute(route)
}

object Main {
  def main(args: Array[String]) {
    
    def logger = LoggerFactory.getLogger(this.getClass())
    implicit lazy val system = ActorSystem("game-of-life-system")

    val router = system.actorOf(Props[Router], "router")

    logger.info("Started server on localhost:9909 for %s".format("game-of-life-system"))
    IO(Http) ! Http.Bind(router, interface = "localhost", port = 9909)

    system.awaitTermination()
  }
}