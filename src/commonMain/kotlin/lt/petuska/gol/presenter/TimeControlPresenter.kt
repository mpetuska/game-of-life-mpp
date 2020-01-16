package lt.petuska.gol.presenter

import lt.petuska.gol.redux.AppConfigAction
import lt.petuska.gol.redux.AppConfigState
import lt.petuska.gol.redux.AppConfigStore
import lt.petuska.gol.redux.UniverseAction
import lt.petuska.gol.redux.UniverseStore
import lt.petuska.gol.view.TimeControlView

class TimeControlPresenter(override val view: TimeControlView) : Presenter() {
  private val subscription = AppConfigStore.subscribe { state ->
    view.showRunning(state.running)
  }
  
  override fun initialise() {
    view.showRunning(AppConfigState().running)
  }
  
  override fun destroy() {
    subscription()
  }
  
  fun handleTick() {
    UniverseStore.dispatch(UniverseAction.Tick)
  }
  
  fun handleTimeToggle() {
    AppConfigStore.dispatch(AppConfigAction.ToggleRunning)
  }
}