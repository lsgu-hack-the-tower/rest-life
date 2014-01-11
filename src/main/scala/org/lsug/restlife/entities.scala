package org.lsug.restlife

case class Cell(x: Int, y: Int)  {
  def isNeighbour(that: Cell): Boolean = ???
  def neighbourCoordinates: Set[(Int, Int)] = Cell.neighbourOffsets.map {
    case (ofX, ofY) => (x + ofX, y + ofY)
  }
}
object Cell {
  val neighbourOffsets = Set((0, -1), (1, -1), (1, 0), (1, 1), (0, 1), (-1, 1), (-1, 0), (-1, -1))
}
case class Board(cells: Array[Cell]) {


  def cell(x: Int, y: Int): Option[Cell] = cells.find(c => c.x == x && c.y == y)


  def isWithinLimits(c: Cell): Boolean = ???
  def neighbours(c: Cell): Set[Cell] = ???
  def nextGeneration: Board = ???
//  Any live cell with fewer than two live neighbours dies, as if caused by under-population.
//  Any live cell with two or three live neighbours lives on to the next generation.
//  Any live cell with more than three live neighbours dies, as if by overcrowding.
//  Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
}
