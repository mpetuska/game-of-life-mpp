package lt.petuska.gol.view

import javafx.event.EventHandler
import javafx.scene.paint.Color
import lt.petuska.gol.presenter.CellPresenter
import tornadofx.*

actual class CellView(x: Int, y: Int) : View, Fragment() {
    actual override val presenter = CellPresenter(this, x, y)

    actual fun showAlive(alive: Boolean) {
        root.style(append = true) {
            backgroundColor.elements.clear()
            backgroundColor += if (alive) Color.BLACK else Color.WHITE
        }
    }

    override val root = button {
        style {
            padding = box(0.px)
            borderColor += box(Color.GRAY)
            backgroundColor += Color.WHITE
            minWidth = 16.px
            minHeight = 16.px
            prefWidth = minWidth
            prefHeight = minHeight
        }
        onMouseClicked = EventHandler {
            presenter.handleClick()
        }
    }

    override fun onDelete() = destroy()

    init {
        presenter.initialise()
    }
}