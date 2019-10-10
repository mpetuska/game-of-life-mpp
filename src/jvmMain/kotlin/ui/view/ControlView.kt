package lt.petuska.game.of.life.mpp.ui.view

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.util.StringConverter
import javafx.util.converter.NumberStringConverter
import lt.petuska.game.of.life.mpp.redux.AppStore
import lt.petuska.game.of.life.mpp.ui.model.ControlModel
import lt.petuska.game.of.life.mpp.ui.presenter.ControlPresenter
import tornadofx.*


actual class ControlView : View<ControlModel>, tornadofx.View() {
    override val presenter = ControlPresenter(this)

    private var isTimeRunning = SimpleBooleanProperty(AppStore.state.isTimeRunning)
    private var fps = SimpleIntegerProperty(AppStore.state.fps)
    private var universeWidth = SimpleIntegerProperty(AppStore.state.universeWidth)
    private var universeHeight = SimpleIntegerProperty(AppStore.state.universeHeight)

    override val root = vbox {
        hbox {
            label("Width")
            textfield {
                prefWidth = 50.0
                textProperty().bindBidirectional(universeWidth, NumberStringConverter())
            }
            label("Height")
            textfield {
                prefWidth = 50.0
                textProperty().bindBidirectional(universeHeight, NumberStringConverter())
            }
            button("Resize & Reset") {
                setOnMouseClicked {
                    presenter.handleResetResize(universeWidth.value, universeHeight.value)
                }
            }
        }
        hbox {
            label("FPS")
            slider(presenter.minFps, presenter.maxFps) {
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
        hbox {
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
        style {
            spacing = 8.px
            paddingTop = 8
            paddingBottom = 8
        }
        children.style {
            alignment = Pos.CENTER
            spacing = 8.px
        }
    }

    actual fun onUniverseDimensionsChange(width: Int, height: Int) {
        universeWidth.value = width
        universeHeight.value = height
    }

    actual fun onFpsChange(newFps: Int) {
        fps.value = newFps
    }

    actual fun onTimeStateChange(timeRunning: Boolean) {
        isTimeRunning.value = timeRunning
    }

    override fun onDelete() = presenter.onDestroy()
}