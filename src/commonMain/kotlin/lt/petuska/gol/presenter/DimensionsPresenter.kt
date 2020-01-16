package lt.petuska.gol.presenter

import lt.petuska.gol.model.Dimensions
import lt.petuska.gol.model.Universe
import lt.petuska.gol.redux.AppConfigAction
import lt.petuska.gol.redux.AppConfigState
import lt.petuska.gol.redux.AppConfigStore
import lt.petuska.gol.redux.UniverseAction
import lt.petuska.gol.redux.UniverseStore
import lt.petuska.gol.view.DimensionsView

class DimensionsPresenter(override val view: DimensionsView) : Presenter() {
  private val subscription = AppConfigStore.subscribe { state ->
    view.showDimensions(state.universeDimensions)
  }
  
  override fun initialise() {
    view.showDimensions(AppConfigState().universeDimensions)
  }
  
  override fun destroy() {
    subscription()
  }
  
  fun handleDimensionsChange(dimensions: Dimensions) {
    AppConfigStore.dispatch(AppConfigAction.SetUniverseDimensions(dimensions))
    UniverseStore.dispatch(UniverseAction.SetUniverse(Universe(dimensions)))
    AppConfigStore.dispatch(AppConfigAction.SetRunning(false))
  }
}