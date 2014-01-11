package org.lsug.restlife.test

import org.specs2.mutable._
import org.lsug.restlife.{Cell, Board}

trait BoardData {
  val c23 = Cell(2, 3)
  val c45 = Cell(4, 5)
  val c56 = Cell(5, 6)

  val b1 = Board(Set(c23, c45, c56))
  val emptyBoard = Board(Set.empty[Cell])
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

        Cell(1,1).neighbourCoordinates should equalTo(Set((0, 0), (0, 1), (0, 2), (1, 0), (1, 2), (2, 0), (2, 1), (2, 2)))
      }
    }
    "Board" should {
      "correctly detect two cells are neighbours or not" in {
        Cell(2,3).isNeighbour(c45) should beFalse
        Cell(4,5).isNeighbour(c56) should beTrue
      }
      "produce a list of neighbours of a cell" in {
        b1.neighbours(c23) should beEmpty
        b1.neighbours(c45) should equalTo(Set(c56))
      }
      "create new cells at positions with 3 alive neighbours" in {
        val expectedNewCell = Cell(0,1)
        val lifeGivingBoard = Board(Set(
          Cell(0,0), Cell(1,1), Cell(1,0)
        ))
        val newCells =lifeGivingBoard.spawnCells
        newCells.size should equalTo(1)
        newCells should equalTo(Set(expectedNewCell))
      }
      "kill cells that have no neighbours" in  {
        val board =  b1.nextGeneration
        board.cell(2,3) should beNone
      }

      "kill cells that only have one neighbour" in {
        val c00 = Cell(0, 0)
        val c01 = Cell(0, 1)

        val b2 = Board(Set(c00, c01))
        val board2  = b2.nextGeneration

        board2.cell(0,0) should beNone
        board2.cell(0,1) should beNone
      }
    }
  }
}
