package lt.petuska.game.of.life.mpp.domain.game

class Universe(initialState: MutableList<MutableList<Cell>>) :
    MutableList<MutableList<Cell>> by ArrayList(initialState) {
    constructor(width: Int, height: Int = width) : this(MutableList<MutableList<Cell>>(width) { x ->
        MutableList<Cell>(height) { y ->
            Cell(x, y)
        }
    })

    val width: Int
        get() = size
    val height: Int
        get() = this.getOrNull(0)?.size ?: 0

    operator fun get(x: Int, y: Int): Cell {
        return this[width.loop(x)][height.loop(y)]
    }

    operator fun set(x: Int, y: Int, cell: Cell) {
        this[width.loop(x)][height.loop(y)] = cell
    }

    private fun Int.loop(value: Int): Int = when {
        value < 0 -> loop(this + value)
        value >= this -> loop(value - this)
        else -> value
    }

    fun forEachCell(block: (x: Int, y: Int, cell: Cell) -> Unit) {
        for (x in 0..width) {
            for (y in 0..height) {
                block(x, y, this[x, y])
            }
        }
    }

    fun copy() = Universe(width, height).apply {
        this@Universe.forEachCell { x, y, cell ->
            this@apply[x, y] = cell.copy()
        }
    }
}