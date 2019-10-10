package lt.petuska.game.of.life.mpp.game

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lt.petuska.game.of.life.mpp.domain.game.Cell
import lt.petuska.game.of.life.mpp.domain.game.Universe
import lt.petuska.game.of.life.mpp.redux.AppStore

object GameEngine {
    private var isRunning: Boolean = false
    private var fps: Int = 1
    private val subscriptions = mutableMapOf<String, MutableSet<(Cell) -> Unit>>()
    private var universe: Universe

    init {
        universe = Universe(AppStore.state.universeWidth, AppStore.state.universeHeight)
        AppStore.subscribe {
            AppStore.state.run {
                isRunning = isTimeRunning
                GameEngine.fps = fps

                if (universe.width != universeWidth || universe.height != universeHeight) {
                    universe = Universe(universeWidth, universeHeight)
                }
            }
        }
        GlobalScope.launch {
            while (true) {
                if (isRunning) {
                    tick()
                }
                delay(1000L / fps)
            }
        }
    }

    fun tick() {
        universe.copy().run {
            forEachCell { x, y, cell ->
                val next = cell.copy(isAlive = nextState(x, y))
                if (next != cell) {
                    universe[x, y] = next
                    subscriptions[next.subscriptionKey]?.forEach { it(next) }
                }
            }
        }
    }

    fun reset() {
        for (x in 0 until universe.width) {
            for (y in 0 until universe.height) {
                universe[x, y] = Cell(x, y).also {
                    subscriptions[it.subscriptionKey]?.forEach { sub ->
                        sub(it)
                    }
                }
            }
        }
    }

    private fun Universe.nextState(x: Int, y: Int): Boolean {
        val isAlive = this[x, y].isAlive
        val aliveNeighborCount = countAliveNeighbors(x, y)
        return when {
            isAlive && (aliveNeighborCount > 3 || aliveNeighborCount < 2) -> false
            !isAlive && aliveNeighborCount == 3 -> true
            isAlive && (aliveNeighborCount == 2 || aliveNeighborCount == 3) -> true
            else -> isAlive
        }
    }

    private fun Universe.countAliveNeighbors(x: Int, y: Int): Int {
        var count = 0
        for (dX in -1..1) {
            for (dY in -1..1) {
                if (dX == 0 && dY == 0) {
                    continue
                } else {
                    if (this[x + dX, y + dY].isAlive) count++
                }
            }
        }
        return count
    }

    fun subscribeToCell(x: Int, y: Int, block: (Cell) -> Unit): () -> Unit {
        val subscriptionKey = universe[x, y].subscriptionKey
        val subs = subscriptions[subscriptionKey] ?: run {
            val new = mutableSetOf<(Cell) -> Unit>()
            subscriptions[subscriptionKey] = new
            new
        }
        subs.add(block)
        return { subs.remove(block) }
    }

    fun toggleCell(x: Int, y: Int) {
        universe[x, y].let {
            universe[x, y] = it.copy(isAlive = !it.isAlive)
            subscriptions[it.subscriptionKey]?.forEach { it(universe[x, y]) }
        }
    }

    private val Cell.subscriptionKey
        get() = "$x,$y"
}




