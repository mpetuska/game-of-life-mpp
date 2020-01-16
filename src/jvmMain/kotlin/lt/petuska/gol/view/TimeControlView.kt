package lt.petuska.gol.view

import javafx.beans.property.SimpleBooleanProperty
import javafx.util.StringConverter
import lt.petuska.gol.presenter.TimeControlPresenter
import tornadofx.button
import tornadofx.hbox

actual class TimeControlView : View, tornadofx.View() {
    actual override val presenter = TimeControlPresenter(this)

    actual fun showRunning(running: Boolean) {
        isTimeRunning.value = running
    }

    private var isTimeRunning = SimpleBooleanProperty(false)

    override val root = hbox {
        button {
            textProperty().bindBidirectional(isTimeRunning, object : StringConverter<Boolean>() {
                override fun toString(`object`: Boolean?) = if (`object` == true) "Stop" else "Start"
                override fun fromString(string: String?) = string == "Stop"
            })
            setOnMouseClicked {
                presenter.handleTimeToggle()
            }
        }
        button {
            text = "Tick"
            setOnMouseClicked {
                presenter.handleTick()
            }
        }
    }

    override fun onDelete() = destroy()

    init {
        presenter.initialise()
    }
}