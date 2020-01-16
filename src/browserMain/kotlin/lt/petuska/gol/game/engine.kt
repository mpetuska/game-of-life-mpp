package lt.petuska.gol.game

import lt.petuska.gol.redux.AppConfigState
import lt.petuska.gol.redux.AppConfigStoreImpl
import lt.petuska.gol.redux.UniverseAction
import lt.petuska.gol.redux.UniverseStoreImpl
import lt.petuska.kvdom.dom.node.setInterval
import lt.petuska.kvdom.dom.window

private var engineRunning = false
actual fun startEngine(appConfigStore: AppConfigStoreImpl, universeStore: UniverseStoreImpl) {
  if (!engineRunning) {
    engineRunning = true
    var state = AppConfigState()
    var executionId: Int? = null
    fun onState(newState: AppConfigState) {
      if (state.fps != newState.fps) {
        executionId?.let {
          window.clearInterval(it)
        }
        executionId = null
      }
      state = newState
      if (executionId == null) {
        executionId = window.setInterval(1000 / state.fps) {
          if (state.running) {
            universeStore.dispatch(UniverseAction.Tick)
          }
        }
      }
    }
    appConfigStore.subscribe {
      onState(it)
    }
    onState(state)
  } else {
    println("Engine already running")
  }
}