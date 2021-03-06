package org.lsug.restlife

case class Cell(x: Int, y: Int)  {
  def isNeighbour(that: Cell): Boolean =
    neighbourCoordinates.contains((that.x, that.y))
  def neighbourCoordinates: Set[(Int, Int)] = Cell.neighbourOffsets.map {
    case (ofX, ofY) => (x + ofX, y + ofY)
  }
}
object Cell {
  val neighbourOffsets = Set((0, -1), (1, -1), (1, 0), (1, 1), (0, 1), (-1, 1), (-1, 0), (-1, -1))
}
case class Board(cells: Set[Cell]) {


  def cell(x: Int, y: Int): Option[Cell] = cells.find(c => c.x == x && c.y == y)


  def isWithinLimits(c: Cell): Boolean = ???
  def neighbours(c: Cell): Set[Cell] = cells.filter(other => c.isNeighbour(other))
  def spawnCells:Set[Cell] = {
    cells.flatMap(c => c.neighbourCoordinates).filter { case (x, y) =>
      cell(x, y).isEmpty && neighbours(Cell(x, y)).size == 3
    } map {
      case (x,y) => Cell(x,y)
    }
  }
  def nextGeneration: Board = {
    val afterDeath = cells.filter { c =>
      val n = neighbours(c)
      n.size > 1 && n.size < 4
    }

    Board(afterDeath ++ spawnCells)
  }
}
