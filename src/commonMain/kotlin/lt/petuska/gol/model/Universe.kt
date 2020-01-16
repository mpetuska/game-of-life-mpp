package lt.petuska.gol.model

class Universe(
  val dimensions: Dimensions,
  private val grid: List<List<Cell>> = List(dimensions.width) { x ->
    List(dimensions.height) { y ->
      Cell(x, y)
    }
  }
) {
  operator fun get(x: Int, y: Int) = grid[x.loop(dimensions.width)][y.loop(dimensions.height)].alive
  operator fun set(x: Int, y: Int, alive: Boolean) {
    grid[x.loop(dimensions.width)][y.loop(dimensions.height)].alive = alive
  }
  
  private fun Int.loop(max: Int): Int = when {
    this < 0 -> (max + this).loop(max)
    this >= max -> (max - this).loop(max)
    else -> this
  }
  
  fun copy() = Universe(dimensions, List(dimensions.width) { x ->
    List(dimensions.height) { y ->
      Cell(x, y, this[x, y])
    }
  })
  
  
  private fun nextCellState(x: Int, y: Int): Boolean {
    var aliveNeighbours = 0
    val isAlive = this[x, y]
    for (dx in (x - 1)..(x + 1)) {
      for (dy in (y - 1)..(y + 1)) {
        if (!(dx == x && dy == y) && this[dx, dy]) {
          aliveNeighbours++
        }
      }
    }
    
    return when {
      isAlive && (2..3).contains(aliveNeighbours) -> true
      !isAlive && aliveNeighbours == 3 -> true
      else -> false
    }
  }
  
  fun nextState(): Universe {
    val next = Universe(dimensions)
    for (x in 0 until dimensions.width) {
      for (y in 0 until dimensions.height) {
        next[x, y] = nextCellState(x, y)
      }
    }
    return next
  }
}