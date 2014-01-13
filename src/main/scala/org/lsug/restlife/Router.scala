package org.lsug.restlife

import spray.json._
import spray.routing.HttpServiceActor
import akka.actor.{Props, ActorSystem, Actor}
import spray.httpx.SprayJsonSupport._
import scala.annotation.tailrec
import akka.io.IO
import spray.can.Http
import org.slf4j.LoggerFactory
import java.util.Date

case class BoardRequest(board: Board, steps: Int)

object BoardJsonProtocol extends DefaultJsonProtocol {
  implicit val cellFormat = jsonFormat2(Cell.apply _)
  implicit val boardFormat = jsonFormat1(Board)
  implicit val boardRequestFormat = jsonFormat2(BoardRequest)
}

class Router extends HttpServiceActor {
  import BoardJsonProtocol._
  
 def logger = LoggerFactory.getLogger(this.getClass()) 

  @tailrec
  private def runBoard(board: Board, steps: Int, states: List[Board] = Nil): List[Board] =
    if (steps == 0) states
    else runBoard(board.nextGeneration, steps - 1, board :: states)

  def receive: Actor.Receive = runRoute {
    logger.info("RUNROUTE")
    pathSingleSlash {
      get { complete { new Date().toString() } } ~
      post {
        logger.info("POST")
        entity(as[BoardRequest]) {
          case BoardRequest(board, steps) =>
            logger.info("ENTITY")
            complete {
              logger.info("COMPLETE")
              runBoard(board, steps)
            }
          case _ =>
            logger.info("No BoardRequest in request")
            complete("MISSED")
        }
      }
    }
  }
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