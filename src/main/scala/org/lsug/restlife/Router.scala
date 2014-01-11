package org.lsug.restlife

import spray.json._
import spray.routing.HttpServiceActor
import akka.actor.{Props, ActorSystem, Actor}
import spray.httpx.SprayJsonSupport._
import scala.annotation.tailrec
import akka.io.IO
import spray.can.Http

case class BoardRequest(board: Board, steps: Int)

object BoardJsonProtocol extends DefaultJsonProtocol {
  implicit val cellFormat = jsonFormat2(Cell.apply _)
  implicit val boardFormat = jsonFormat1(Board)
  implicit val boardRequestFormat = jsonFormat2(BoardRequest)
}

class Router extends HttpServiceActor {
  import BoardJsonProtocol._

  @tailrec
  private def runBoard(board: Board, steps: Int, states: List[Board] = Nil): List[Board] =
    if (steps == 0) states
    else runBoard(board.nextGeneration, steps - 1, board :: states)

  def receive: Actor.Receive = runRoute {
    println("RUNROUTE")
    pathSingleSlash {
      get { complete { "OK" } } ~
      post {
        println("POST")
        entity(as[BoardRequest]) {
          case BoardRequest(board, steps) =>
            println("ENTITY")
            complete {
              println("COMPLETE")
              runBoard(board, steps)
            }
          case _ =>
            println("OOPS")
            complete("MISSED")
        }
      }
    }
  }
}

object Main {
  def main(args: Array[String]) {
    implicit lazy val system = ActorSystem("game-of-life-system")

    val router = system.actorOf(Props[Router], "router")

    IO(Http) ! Http.Bind(router, interface = "localhost", port = 9909)

    system.awaitTermination()
  }
}