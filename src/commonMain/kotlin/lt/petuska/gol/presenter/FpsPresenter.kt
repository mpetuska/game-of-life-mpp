package lt.petuska.gol.presenter

import lt.petuska.gol.redux.AppConfigAction
import lt.petuska.gol.redux.AppConfigState
import lt.petuska.gol.redux.AppConfigStore
import lt.petuska.gol.view.FpsView

class FpsPresenter(override val view: FpsView) : Presenter() {
  private val subscription = AppConfigStore.subscribe { state ->
    view.showFps(state.fps)
  }
  
  override fun initialise() {
    view.showFps(AppConfigState().fps)
  }
  
  override fun destroy() {
    subscription()
  }
  
  fun handleFpsChange(fps: Int) {
    AppConfigStore.dispatch(AppConfigAction.SetFps(fps))
  }
}