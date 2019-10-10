package lt.petuska.game.of.life.mpp.ui.view

import javafx.collections.ObservableList
import javafx.geometry.Pos
import lt.petuska.game.of.life.mpp.redux.AppStore
import lt.petuska.game.of.life.mpp.ui.model.UniverseModel
import lt.petuska.game.of.life.mpp.ui.presenter.UniversePresenter
import tornadofx.bindChildren
import tornadofx.hbox
import tornadofx.observableList
import tornadofx.vbox

actual class UniverseView : View<UniverseModel>, tornadofx.View() {
    override val presenter = UniversePresenter(this)
    private val rows = observableList<Int>().resize(AppStore.state.universeHeight)
    private val columns = observableList<Int>().resize(AppStore.state.universeWidth)
    override val root = vbox {
        alignment = Pos.CENTER
        bindChildren(rows) { y ->
            hbox {
                alignment = Pos.CENTER
                bindChildren(columns) { x ->
                    CellView(x, y).root
                }
            }
        }
    }

    actual fun updateDimensions(width: Int, height: Int) {
        rows.resize(height)
        columns.resize(width)
        currentStage?.sizeToScene()
    }

    private fun ObservableList<Int>.resize(desiredSize: Int): ObservableList<Int> = apply {
        if (size > desiredSize) {
            remove(desiredSize, size)
        } else if (size < desiredSize) {
            add(size, size)
            resize(desiredSize)
        }
    }

    override fun onDelete() = presenter.onDestroy()
}