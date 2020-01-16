package lt.petuska.gol.view

import lt.petuska.gol.gtk3.Application
import lt.petuska.gol.gtk3.VBox
import lt.petuska.gol.gtk3.Window
import lt.petuska.gol.presenter.AppPresenter
import lt.petuska.gol.presenter.Presenter

actual class AppView(app: Application) : View, Window(app) {
  init {
    title = "Conway's Game of Life"
    val box = VBox()
    box.attach(UniverseView())
    box.attach(ControlView())
    add(box)
  }
  
  actual override val presenter = AppPresenter(this).also(Presenter::initialise)
  
  actual fun showApp() {
    showAll()
  }
}