package lt.petuska.gol.redux

import lt.petuska.gol.game.Coordinates
import lt.petuska.gol.model.Universe
import org.reduxkotlin.StoreSubscription

data class UniverseState(
  val universe: Universe = Universe(AppConfigState().universeDimensions)
)

sealed class UniverseAction {
  data class SetUniverse(val universe: Universe) : UniverseAction()
  data class SetCellState(val coordinates: Coordinates, val alive: Boolean) : UniverseAction()
  data class ToggleCellState(val coordinates: Coordinates) : UniverseAction()
  object Tick : UniverseAction()
}

val UniverseStore = UniverseStoreImpl()

class UniverseStoreImpl : ReduxStore<UniverseState, UniverseAction>(UniverseState()) {
  private val subscriptions = mutableMapOf<Pair<Int, Int>, (Boolean) -> Unit>()
  
  fun subscribeToCellState(coordinates: Coordinates, subscriber: (Boolean) -> Unit): StoreSubscription {
    subscriptions[coordinates] = subscriber
    return {
      subscriptions.remove(coordinates)
    }
  }
  
  private var old = state.universe
  
  init {
    subscribe { state ->
      val new = state.universe
      if (old.dimensions != new.dimensions) {
        subscriptions.forEach { it.value(new[it.key.first, it.key.second]) }
      } else {
        for (x in 0 until new.dimensions.width) {
          for (y in 0 until new.dimensions.height) {
            if (old[x, y] != new[x, y]) {
              subscriptions[Pair(x, y)]?.invoke(new[x, y])
            }
          }
        }
      }
      old = new
    }
  }
  
  override fun reduce(state: UniverseState, action: Any): UniverseState = when (val a = action as? UniverseAction) {
    null -> state
    is UniverseAction.SetUniverse -> state.copy(universe = a.universe)
    is UniverseAction.SetCellState -> {
      val next = state.universe.copy()
      next[a.coordinates.first, a.coordinates.second] = a.alive
      state.copy(universe = next)
    }
    is UniverseAction.ToggleCellState -> {
      val next = state.universe.copy()
      next[a.coordinates.first, a.coordinates.second] = !next[a.coordinates.first, a.coordinates.second]
      state.copy(universe = next)
    }
    UniverseAction.Tick -> state.copy(universe = state.universe.nextState())
  }
}