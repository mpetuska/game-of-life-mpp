package lt.petuska.gol.view

import lt.petuska.gol.gtk3.VBox
import lt.petuska.gol.presenter.ControlPresenter
import lt.petuska.gol.presenter.Presenter

actual class ControlView : View, VBox() {
  init {
    spacing = 1
    add(DimensionsView())
    add(FpsView())
    add(TimeControlView())
  }
  
  actual override val presenter = ControlPresenter(this).also(Presenter::initialise)
}