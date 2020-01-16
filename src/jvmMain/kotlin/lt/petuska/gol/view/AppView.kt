package lt.petuska.gol.view

import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import lt.petuska.gol.presenter.AppPresenter
import tornadofx.*

actual class AppView : View, tornadofx.View("Conway's Game of Life") {
  actual override val presenter = AppPresenter(this)
  
  actual fun showApp() {
    val new = ScrollPane().apply {
      isFitToHeight = true
      isFitToWidth = true
      vbox {
        alignment = Pos.CENTER
        add(UniverseView())
        add(ControlView())
        style {
          padding = box(8.px)
        }
      }
    }
    new.isFitToHeight = true
    new.isFitToWidth = true
    root.replaceChildren(new)
  }
  
  override val root = vbox()
  
  override fun onDelete() = destroy()
  
  init {
    presenter.initialise()
  }
}