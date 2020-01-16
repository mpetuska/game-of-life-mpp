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
import kotlinx.css.marginRight
import kotlinx.css.px
import kotlinx.css.width
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import lt.petuska.gol.model.Dimensions
import lt.petuska.gol.presenter.DimensionsPresenter
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.setState
import styled.css
import styled.styledButton
import styled.styledDiv
import styled.styledInput
import styled.styledLabel

actual class DimensionsView(props: Props) : View, ViewComponent<DimensionsView.Props>(props) {
    actual override val presenter = DimensionsPresenter(this)

    actual fun showDimensions(dimensions: Dimensions) = setState {
        width = dimensions.width
        height = dimensions.height
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                alignItems = Align.center
                justifyContent = JustifyContent.spaceAround
                margin = "0.5em"
            }
            dimensionInput(state.width, "Width") {
                setState {
                    width = it
                }
            }
            dimensionInput(state.height, "Height") {
                setState {
                    height = it
                }
            }
            styledButton {
                css.margin = "0.5em"
                attrs.onClickFunction = {
                    presenter.handleDimensionsChange(Dimensions(state.width, state.height))
                }
                +"Resize/Reset"
            }
        }
    }

    private fun RBuilder.dimensionInput(currentValue: Int, label: String, onChange: (Int) -> Unit) {
        styledDiv {
            css.margin = "0.25em"
            styledLabel {
                css.marginRight = 8.px
                +label
            }
            styledInput(type = InputType.number) {
                css {
                    width = 32.px
                }
                attrs {
                    value = "$currentValue"
                    onChangeFunction = {
                        (it.target as HTMLInputElement).value.toIntOrNull()?.let { value ->
                            onChange(value)
                        }
                    }
                }
            }
        }
    }

    interface Props : Model {
        var width: Int
        var height: Int
    }
}

fun RBuilder.dimensionsView() = child(DimensionsView::class) {
    attrs {
      width = 0
      height = 0
    }
}