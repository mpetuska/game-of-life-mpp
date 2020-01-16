package lt.petuska.gol.game

import kotlinx.cinterop.StableRef
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import lt.petuska.gol.redux.AppConfigState
import lt.petuska.gol.redux.AppConfigStoreImpl
import lt.petuska.gol.redux.UniverseAction
import lt.petuska.gol.redux.UniverseStoreImpl
import kotlin.native.concurrent.AtomicInt
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.freeze

@SharedImmutable
val engineRunning = AtomicInt(0)
@SharedImmutable
private val engineDispatcher = newSingleThreadContext("engine")

actual fun startEngine(appConfigStore: AppConfigStoreImpl, universeStore: UniverseStoreImpl) {
  if (engineRunning.value == 0) {
    engineRunning.increment()
    val stateChannel = Channel<AppConfigState>().freeze()
    val dispatchChannel = Channel<Int>()
    val universeStorePtr = StableRef.create(universeStore).freeze()
    GlobalScope.launch {
      for (action in dispatchChannel) {
        universeStorePtr.get().dispatch(UniverseAction.Tick)
      }
    }
    appConfigStore.subscribe {
      GlobalScope.launch(engineDispatcher) { stateChannel.send(it.freeze()) }
    }
    GlobalScope.launch(engineDispatcher) {
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
    println("engine already running")
  }
}