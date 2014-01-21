package org.lsug.restlife.test

import org.specs2.mutable._
import org.lsug.restlife.LifeSym

trait BoardData {
  import LifeSym._

  val c23: Cell = (2, 3)
  val c45: Cell = (4, 5)
  val c56: Cell = (5, 6)

  val b1: Board = Set(c23, c45, c56)
  val emptyBoard: Board = Set.empty
}

class BoardTest extends Specification {

  import LifeSym._

  new BoardData {
    "LifeSym" should {
      "correctly identify neighbouring coordinates" in {
        neighbourCoordinates((1,1)) should equalTo(Set((0, 0), (0, 1), (0, 2), (1, 0), (1, 2), (2, 0), (2, 1), (2, 2)))
      }
      "correctly detect two cells are neighbours or not" in {
        areNeighbours((2, 3), c45) should beFalse
        areNeighbours((4, 5), c56) should beTrue
      }
      "produce a list of living neighbours of a c" in {
        livingNeighbours(b1, c23) should beEmpty
        livingNeighbours(b1, c45) should equalTo(Set(c56))
      }
      "create new cells at positions with 3 alive neighbours" in {
        val expectedNewCell = (0, 1)
        val lifeGivingBoard = Set((0, 0), (1, 1), (1, 0))
        val nextGen = nextGeneration(lifeGivingBoard)

        nextGen.size should equalTo(4)
        nextGen should equalTo(lifeGivingBoard ++ Set(expectedNewCell))
      }
      "kill cells that have no neighbours" in  {
        val board =  nextGeneration(b1)
        board should not contain((2,3))
      }
      "kill cells that only have one neighbour" in {
        val c00 = (0, 0)
        val c01 = (0, 1)

        val b2 = Set(c00, c01)
        val board2  = nextGeneration(b2)

        board2 should not contain((0,0))
        board2 should not contain((0,1))
      }
      "c with two neighbours should survive" in {
        val c00 = (0, 0)
        val c01 = (0, 1)
        val c02 = (0, 2)

        val b3 = Set(c00, c01, c02)
        val board3  = nextGeneration(b3)

        board3 should not contain((0,0))
        board3 should contain((0,1))
        board3 should not contain((0,2))
      }
      "correctly evolve a 3 vertical pattern" in {
        val b = board(cell(3, 2), cell(3, 3), cell(3, 4))
        val bs = sym(b, 4)

        bs should equalTo {
          board(cell(2, 3), cell(3, 3), cell(4, 3)) ::
          board(cell(3, 2), cell(3, 3), cell(3, 4)) ::
          board(cell(2, 3), cell(3, 3), cell(4, 3)) ::
          board(cell(3, 2), cell(3, 3), cell(3, 4)) :: Nil
        }
      }
    }
  }
}
