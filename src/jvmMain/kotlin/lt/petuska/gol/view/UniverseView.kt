package lt.petuska.gol.view

import javafx.geometry.Pos
import lt.petuska.gol.model.Dimensions
import lt.petuska.gol.presenter.UniversePresenter
import tornadofx.hbox
import tornadofx.replaceChildren
import tornadofx.vbox

actual class UniverseView : View, tornadofx.View() {
    actual override val presenter = UniversePresenter(this)

    actual fun showUniverse(dimensions: Dimensions) {
        root.replaceChildren {
            for (y in 0 until dimensions.height) {
                hbox {
                    alignment = Pos.CENTER
                    for (x in 0 until dimensions.width) {
                        add(CellView(x, y))
                    }
                }
            }
        }
    }

    override val root = vbox()

    override fun onDelete() = destroy()

    init {
        presenter.initialise()
    }
}