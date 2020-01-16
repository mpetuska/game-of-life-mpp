package lt.petuska.gol.view

import lt.petuska.gol.gtk3.Button
import lt.petuska.gol.gtk3.HBox
import lt.petuska.gol.presenter.Presenter
import lt.petuska.gol.presenter.TimeControlPresenter
import lt.petuska.gol.redux.AppConfigAction
import lt.petuska.gol.redux.AppConfigStore
import lt.petuska.gol.redux.UniverseAction
import lt.petuska.gol.redux.UniverseStore

actual class TimeControlView : View, HBox() {
  private val timeBtn = Button().also {
    it.label = "Start"
    it.onClicked = {
      AppConfigStore.dispatch(AppConfigAction.ToggleRunning)
    }
    add(it)
  }
  
  init {
    spacing = 1
    Button().also {
      it.label = "Tick"
      it.onClicked = {
        UniverseStore.dispatch(UniverseAction.Tick)
      }
      add(it)
    }
  }
  
  actual override val presenter: TimeControlPresenter = TimeControlPresenter(this).also(Presenter::initialise)
  
  actual fun showRunning(running: Boolean) {
    timeBtn.label = if (running) "Stop" else "Start"
  }
}