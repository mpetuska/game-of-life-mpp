package lt.petuska.gol.game

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import lt.petuska.gol.redux.AppConfigState
import lt.petuska.gol.redux.AppConfigStoreImpl
import lt.petuska.gol.redux.UniverseAction
import lt.petuska.gol.redux.UniverseStoreImpl

private var engineRunning = false
actual fun startEngine(appConfigStore: AppConfigStoreImpl, universeStore: UniverseStoreImpl) {
  if (!engineRunning) {
    engineRunning = true
    val stateChannel = Channel<AppConfigState>()
    val dispatchChannel = Channel<Int>()
    GlobalScope.launch {
      for (action in dispatchChannel) {
        universeStore.dispatch(UniverseAction.Tick)
      }
    }
    appConfigStore.subscribe {
      GlobalScope.launch { stateChannel.send(it) }
    }
    GlobalScope.launch {
      var state = AppConfigState()
      while (isActive) {
        if (!stateChannel.isEmpty) {
          state = stateChannel.receive()
        }
        if (state.running) {
          dispatchChannel.send(0)
        }
        delay((1000 / state.fps).toLong())
      }
    }
  } else {
    println("Engine already running")
  }
}