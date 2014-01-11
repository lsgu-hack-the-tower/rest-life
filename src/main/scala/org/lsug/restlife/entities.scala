package org.lsug.restlife

case class Cell(x: Int, y: Int)
case class Board(cells: Array[Cell]) {
  val neighbourOffsets = Set((0, -1), (1, -1), (1, 0), (1, 1), (0, 1), (-1, 1), (-1, 0), (-1, -1))

  def cell(x: Int, y: Int): Option[Cell] = cells.find(c => c.x == x && c.y == y)
  def neighbourCoordinates(x: Int, y: Int): Set[(Int, Int)] = neighbourOffsets.map {
    case (ofX, ofY) => (x + ofX, y + ofY)
  }
  def isNeighbour(c1: Cell, c2: Cell): Boolean = ???
  def isWithinLimits(c: Cell): Boolean = ???
  def neighbours(c: Cell): Set[Cell] = ???
  def nextGeneration: Board = ???
//  Any live cell with fewer than two live neighbours dies, as if caused by under-population.
//  Any live cell with two or three live neighbours lives on to the next generation.
//  Any live cell with more than three live neighbours dies, as if by overcrowding.
//  Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
}
