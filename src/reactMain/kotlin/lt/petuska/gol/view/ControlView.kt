package lt.petuska.gol.view

import kotlinx.css.*
import lt.petuska.gol.presenter.ControlPresenter
import react.RBuilder
import styled.css
import styled.styledDiv
import styled.styledHr

actual class ControlView(props: Model) : View, ViewComponent<Model>(props) {
    actual override val presenter = ControlPresenter(this)

    private fun RBuilder.separator() = styledHr {
        css {
            borderStyle = BorderStyle.dashed
            borderWidth = 0.5.px
        }
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                width = LinearDimension.fitContent
                borderColor = Color.black
                borderStyle = BorderStyle.solid
                borderWidth = 1.px
                margin = "auto"
                marginTop = 0.5.em
            }
            dimensionsView()
            separator()
            fpsView()
            separator()
            timeControlView()
        }
    }
}

fun RBuilder.controlView() = child(ControlView::class) {}