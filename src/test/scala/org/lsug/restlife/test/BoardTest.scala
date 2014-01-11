package org.lsug.restlife.test

import org.specs2.mutable._
import org.lsug.restlife.{Cell, Board}

trait BoardData {
  val c23 = Cell(2, 3)
  val c45 = Cell(4, 5)
  val c66 = Cell(5, 6)

  val b1 = Board(Array(c23, c45, c66))
  val emptyBoard = Board(Array.empty[Cell])
}

class BoardTest extends Specification {

  new BoardData {
    "Cells" should {
      "be found at the right coordinates" in {
        emptyBoard.cell(1, 1) should beNone
        emptyBoard.cell(2, 2) should beNone

        b1.cell(1, 1) should beNone
        b1.cell(2, 2) should beNone
        b1.cell(2, 3) should equalTo(Some(c23))
        b1.cell(4, 5) should equalTo(Some(c45))
      }

      "correctly identify neighbouring coordinates" in {
        b1.neighbourCoordinates(1, 1) should equalTo(Set((0, 0), (0, 1), (0, 2), (1, 0), (1, 2), (2, 0), (2, 1), (2, 2)))
      }
    }
  }
}
