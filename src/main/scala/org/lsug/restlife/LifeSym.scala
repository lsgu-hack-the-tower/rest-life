package org.lsug.restlife

import scala.annotation.tailrec

object LifeSym {
  type Cell = (Int, Int)
  type Board = Set[Cell]

  def x(c: Cell) = c._1
  def y(c: Cell) = c._2
  
  def cell(x: Int, y: Int): Cell = (x, y)
  def board(cs: Cell*): Board = cs.toSet

  val neighbourOffsets: Set[(Int, Int)] = Set((0, -1), (1, -1), (1, 0), (1, 1), (0, 1), (-1, 1), (-1, 0), (-1, -1))

  def neighbourCoordinates(c: Cell): Set[Cell] = neighbourOffsets.map { case (ofX, ofY) => cell(x(c) + ofX, y(c) + ofY) }

  def areNeighbours(c1: Cell, c2: Cell): Boolean = neighbourCoordinates(c1).contains(c2)

  def livingNeighbours(b: Board, c: Cell): Set[Cell] = b.filter(other => areNeighbours(c, other))

  def nextGeneration(b: Board): Board = {
    val survivingCells = b.filter { c =>
      val n = livingNeighbours(b, c)
      n.size > 1 && n.size < 4
    }

    val spawiningCells = b.flatMap(neighbourCoordinates).filter { c =>
      !b.contains(c) && livingNeighbours(b, c).size == 3
    }

    survivingCells ++ spawiningCells
  }

  def sym(b: Board, steps: Int): List[Board] = {
    @tailrec
    def runBoard(b: Board, s: Int, states: List[Board] = Nil): List[Board] =
      if (s == 0) states
      else runBoard(nextGeneration(b), s - 1, b :: states)

    runBoard(b, steps)
  }
}

