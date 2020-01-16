package lt.petuska.gol.view

import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.paint.Color
import javafx.util.converter.NumberStringConverter
import lt.petuska.gol.game.Const.maxFps
import lt.petuska.gol.game.Const.minFps
import lt.petuska.gol.presenter.FpsPresenter
import tornadofx.*

actual class FpsView : View, tornadofx.View() {
    actual override val presenter = FpsPresenter(this)

    actual fun showFps(fps: Int) {
        this.fps.value = fps
    }

    private var fps = SimpleIntegerProperty(0)

    override val root = hbox {
        label("FPS")
        slider(minFps, maxFps) {
            valueProperty().bindBidirectional(fps)
            valueProperty().addListener { _, _, newValue ->
                presenter.handleFpsChange(newValue.toInt())
            }
        }
        label {
            textProperty().bindBidirectional(fps, NumberStringConverter())
            style {
                borderColor += box(Color.GRAY)
                padding = box(1.px)

            }
        }
    }

    override fun onDelete() = destroy()

    init {
        presenter.initialise()
    }
}