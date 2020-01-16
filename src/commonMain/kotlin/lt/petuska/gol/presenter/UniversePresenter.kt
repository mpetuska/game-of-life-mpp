package lt.petuska.gol.presenter

import lt.petuska.gol.model.Dimensions
import lt.petuska.gol.redux.AppConfigState
import lt.petuska.gol.redux.AppConfigStore
import lt.petuska.gol.view.UniverseView

class UniversePresenter(override val view: UniverseView) : Presenter() {
  private val subscription = AppConfigStore.subscribe { state ->
    val new = state.universeDimensions
    if (new != dimensions) {
      dimensions = new
      view.showUniverse(dimensions)
    }
  }
  private var dimensions: Dimensions = AppConfigState().universeDimensions
  
  override fun initialise() {
    view.showUniverse(dimensions)
  }
  
  override fun destroy() {
    subscription()
  }
}