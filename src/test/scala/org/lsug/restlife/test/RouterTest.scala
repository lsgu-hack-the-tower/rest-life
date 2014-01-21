package org.lsug.restlife.test

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import org.lsug.restlife.{BoardJsonProtocol, BoardRequest, Routes, Router}
import spray.http.StatusCodes
import org.lsug.restlife.LifeSym._
import org.slf4j.LoggerFactory

class RouterTest extends Specification with Specs2RouteTest {
  val r = new Routes {
    def logger = LoggerFactory.getLogger(getClass)
  }
  import BoardJsonProtocol._
  import spray.httpx.SprayJsonSupport._

  "The service" should {
    "produce an empty evolution for an empty board" in {
      Post("/", BoardRequest(board(), 3)) ~> r.route ~> check {
        handled must beTrue
        status must beEqualTo(StatusCodes.OK)
        responseAs[List[Board]] should beEqualTo(board() :: board() :: board() :: Nil)
      }
    }

    "produce an evolution respecting the game rules" in {
      val b = board(cell(0, 0), cell(0, 1), cell(1, 1), cell(4, 5))
      val b1 = board(cell(0, 0), cell(0, 1), cell(1, 1), cell(1, 0))
      val req = BoardRequest(b, 2)
      Post("/", req) ~> r.route ~> check {
        handled must beTrue
        status must beEqualTo(StatusCodes.OK)
        responseAs[List[Board]] should beEqualTo(b1 :: b :: Nil)
      }
    }
  }
}
