package lt.petuska.game.of.life.mpp.ui.view

import javafx.geometry.Pos
import lt.petuska.game.of.life.mpp.ui.model.AppModel
import lt.petuska.game.of.life.mpp.ui.presenter.AppPresenter
import tornadofx.*

actual class AppView : View<AppModel>, tornadofx.View("Conway's Game of Life") {
    override val presenter = AppPresenter(this)
    override val root = scrollpane {
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

    override fun onDelete() = presenter.onDestroy()
}