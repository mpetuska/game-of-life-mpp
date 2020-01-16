package lt.petuska.gol.view

import kotlinx.css.*
import kotlinx.html.js.onMouseUpFunction
import lt.petuska.gol.presenter.CellPresenter
import react.RBuilder
import react.setState
import styled.css
import styled.styledButton

actual class CellView(props: Props) : View, ViewComponent<CellView.Props>(props) {
    actual override val presenter = CellPresenter(this, props.x, props.y)

    actual fun showAlive(alive: Boolean) = setState { this.alive = alive }

    override fun RBuilder.render() {
        styledButton {
            css {
                padding = "0px"
                height = 16.px
                width = 16.px
                backgroundColor = if (state.alive) Color.black else Color.white
            }
            attrs.onMouseUpFunction = {
                presenter.handleClick()
            }
        }
    }

    interface Props : Model {
        var x: Int
        var y: Int
        var alive: Boolean
    }
}

fun RBuilder.cellView(x: Int, y: Int) = child(CellView::class) {
    attrs {
        this.x = x
        this.y = y
        this.alive = false
    }
}