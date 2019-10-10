package lt.petuska.game.of.life.mpp.ui.view

import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import lt.petuska.game.of.life.mpp.redux.AppStore
import lt.petuska.game.of.life.mpp.ui.model.ControlModel
import lt.petuska.game.of.life.mpp.ui.presenter.ControlPresenter
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.setState
import styled.*

actual class ControlView(props: Props) : View<ControlModel>, ViewComponent<ControlView.Props>(props) {
    override val presenter = ControlPresenter(this)

    override fun RBuilder.render() {
        styledDiv {
            css {
                margin = "auto"
                width = LinearDimension.fitContent
                borderColor = Color.black
                borderStyle = BorderStyle.solid
                borderWidth = 1.px
                marginTop = 16.px
                padding = "8px"
                display = Display.flex
                flexDirection = FlexDirection.column
            }
            dimensionControl()
            separator()
            speedControl()
            separator()
            timeControl()
        }
    }

    private fun RBuilder.separator() {
        styledHr {
            css {
                borderStyle = BorderStyle.dashed
                borderWidth = 1.px
                borderColor = Color.black
                width = 100.pct
            }
        }
    }

    private fun RBuilder.dimensionControl() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                alignItems = Align.center
                justifyContent = JustifyContent.spaceAround
            }
            dimensionInput(state.universeWidth, "Width") {
                setState {
                    universeWidth = it
                }
            }
            dimensionInput(state.universeHeight, "Height") {
                setState {
                    universeHeight = it
                }
            }
            styledButton {
                attrs.onClickFunction = {
                    presenter.handleResetResize(state.universeWidth, state.universeHeight)
                }
                +"Resize/Reset"
            }
        }
    }

    private fun RBuilder.dimensionInput(currentValue: Int, label: String, onChange: (Int) -> Unit) {
        styledLabel {
            css.marginRight = 8.px
            +label
        }
        styledInput(type = InputType.number) {
            css {
                width = 32.px
                marginRight = 8.px
            }
            attrs {
                value = "$currentValue"
                min = "5"
                max = "99"
                onChangeFunction = {
                    (it.target as HTMLInputElement).value.toIntOrNull()?.let { value ->
                        onChange(value)
                    }
                }
            }
        }
    }

    private fun RBuilder.timeControl() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                alignItems = Align.center
                justifyContent = JustifyContent.center
            }
            styledButton {
                attrs.onClickFunction = {
                    presenter.handleTimeToggle()
                }
                +if (state.isTimeRunning) "Stop " else "Start"
            }
            styledButton {
                attrs.onClickFunction = {
                    presenter.handleTick()
                }
                +"Tick"
            }
        }
    }

    private fun RBuilder.speedControl() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                alignItems = Align.center
                justifyContent = JustifyContent.spaceAround
            }
            styledLabel {
                css.marginRight = 8.px
                +"FPS"
            }
            styledDiv {
                css {
                    display = Display.flex
                    alignItems = Align.center
                    width = 100.pct
                }
                styledInput(type = InputType.range) {
                    css.width = 100.pct
                    attrs {
                        min = "${presenter.minFps}"
                        max = "${presenter.maxFps}"
                        value = "${state.fps}"
                        onChangeFunction = {
                            (it.target as HTMLInputElement?)?.value?.toIntOrNull()?.let { value ->
                                setState {
                                    fps = value
                                }
                            }
                        }
                        onClickFunction = {
                            presenter.handleFpsChange(state.fps)
                        }
                    }
                }
                styledSpan {
                    css {
                        borderWidth = 1.px
                        borderColor = Color.gray
                        borderStyle = BorderStyle.solid
                        padding = "1px"
                    }
                    +"${state.fps}"
                }
            }
        }
    }

    actual fun onUniverseDimensionsChange(width: Int, height: Int) = setState {
        universeWidth = width
        universeHeight = height
    }

    actual fun onFpsChange(newFps: Int) = setState { fps = newFps }

    actual fun onTimeStateChange(timeRunning: Boolean) = setState { isTimeRunning = timeRunning }

    interface Props : ControlModel {
        override var universeWidth: Int
        override var universeHeight: Int
        override var fps: Int
        override var isTimeRunning: Boolean
    }
}

fun RBuilder.controlView() = child(ControlView::class) {
    attrs {
        universeWidth = AppStore.state.universeWidth
        universeHeight = AppStore.state.universeHeight
        fps = AppStore.state.fps
        isTimeRunning = AppStore.state.isTimeRunning
    }
}