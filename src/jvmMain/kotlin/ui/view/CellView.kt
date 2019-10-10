package lt.petuska.game.of.life.mpp.ui.view

import javafx.scene.paint.Color
import lt.petuska.game.of.life.mpp.ui.model.CellModel
import lt.petuska.game.of.life.mpp.ui.presenter.CellPresenter
import tornadofx.*

actual class CellView(x: Int, y: Int) : View<CellModel>, Fragment() {
    override val presenter = CellPresenter(this, x, y)
    override val root = button {
        style {
            padding = box(0.px)
            borderColor += box(Color.GRAY)
            backgroundColor += Color.WHITE
            prefHeight = 16.px
            prefWidth = 16.px
        }
        setOnMouseClicked {
            presenter.handleClick()
        }
    }

    actual fun showAlive(isAlive: Boolean) {
        root.style(true) {
            backgroundColor.elements.clear()
            backgroundColor += if (isAlive) Color.BLACK else Color.WHITE
        }
    }

    override fun onDelete() = presenter.onDestroy()
}