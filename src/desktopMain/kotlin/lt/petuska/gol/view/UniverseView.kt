package lt.petuska.gol.view

import lt.petuska.gol.gtk3.Grid
import lt.petuska.gol.model.Dimensions
import lt.petuska.gol.presenter.Presenter
import lt.petuska.gol.presenter.UniversePresenter

actual class UniverseView : View, Grid() {
  actual override val presenter = UniversePresenter(this).also(Presenter::initialise)
  
  actual fun showUniverse(dimensions: Dimensions) {
    clear()
    for (x in 0 until dimensions.width) {
      for (y in 0 until dimensions.height) {
        attach(CellView(x, y), x, y)
      }
    }
    rowSpacing = 1
    columnSpacing = 1
    rowHomogeneous = true
    columnHomogeneous = true
    showAll()
  }
}