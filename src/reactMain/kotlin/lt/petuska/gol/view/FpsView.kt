package lt.petuska.gol.view

import kotlinx.css.Align
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.JustifyContent
import kotlinx.css.alignItems
import kotlinx.css.borderColor
import kotlinx.css.borderWidth
import kotlinx.css.display
import kotlinx.css.flexDirection
import kotlinx.css.justifyContent
import kotlinx.css.margin
import kotlinx.css.marginRight
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.px
import kotlinx.css.width
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import lt.petuska.gol.game.Const
import lt.petuska.gol.presenter.FpsPresenter
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.setState
import styled.css
import styled.styledDiv
import styled.styledInput
import styled.styledLabel
import styled.styledSpan

actual class FpsView(props: Props) : View, ViewComponent<FpsView.Props>(props) {
    actual override val presenter = FpsPresenter(this)

    actual fun showFps(fps: Int) = setState { this.fps = fps }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                alignItems = Align.center
                justifyContent = JustifyContent.spaceAround
                margin = "0.5em"
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
                      min = "${Const.minFps}"
                      max = "${Const.maxFps}"
                      value = "${state.fps}"
                      onChangeFunction = {
                        (it.target as HTMLInputElement?)?.value?.toIntOrNull()?.let { value ->
                          presenter.handleFpsChange(value)
                        }
                      }
                    }
                }
                styledSpan {
                    css {
                        borderWidth = 1.px
                        borderColor = Color.gray
                        padding = "1px"
                    }
                    +"${state.fps}"
                }
            }
        }
    }

    interface Props : Model {
        var fps: Int
    }
}

fun RBuilder.fpsView() = child(FpsView::class) {
    attrs {
      this.fps = 0
    }
}