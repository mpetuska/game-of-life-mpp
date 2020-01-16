package lt.petuska.gol.view

import kotlinx.css.Align
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.JustifyContent
import kotlinx.css.alignItems
import kotlinx.css.display
import kotlinx.css.flexDirection
import kotlinx.css.justifyContent
import kotlinx.css.margin
import kotlinx.html.js.onClickFunction
import lt.petuska.gol.presenter.TimeControlPresenter
import react.RBuilder
import react.setState
import styled.css
import styled.styledButton
import styled.styledDiv

actual class TimeControlView(props: Props) : View, ViewComponent<TimeControlView.Props>(props) {
    actual override val presenter = TimeControlPresenter(this)

    actual fun showRunning(running: Boolean) = setState { this.running = running }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                alignItems = Align.center
                justifyContent = JustifyContent.center
                margin = "0.5em"
            }
            styledButton {
                attrs.onClickFunction = {
                    presenter.handleTimeToggle()
                }
                +if (state.running) "Stop " else "Start"
            }
            styledButton {
                attrs.onClickFunction = {
                    presenter.handleTick()
                }
                +"Tick"
            }
        }
    }

    interface Props : Model {
        var running: Boolean
    }
}

fun RBuilder.timeControlView() = child(TimeControlView::class) {
    attrs {
      this.running = false
    }
}