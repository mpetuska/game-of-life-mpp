package lt.petuska.game.of.life.mpp.ui.view

import kotlinx.css.*
import kotlinx.html.js.onMouseUpFunction
import lt.petuska.game.of.life.mpp.ui.model.CellModel
import lt.petuska.game.of.life.mpp.ui.presenter.CellPresenter
import react.RBuilder
import react.setState
import styled.css
import styled.styledButton


actual class CellView(props: Props) : View<CellModel>, ViewComponent<CellView.Props>(props) {
    override val presenter = CellPresenter(this, props.x, props.y)

    actual fun showAlive(isAlive: Boolean) = setState { this.isAlive = isAlive }

    override fun RBuilder.render() {
        styledButton {
            css {
                padding = "0px"
                height = 16.px
                width = 16.px
                backgroundColor = if (state.isAlive) Color.black else Color.white
            }
            attrs.onMouseUpFunction = {
                presenter.handleClick()
            }
        }
    }

    interface Props : CellModel {
        override var x: Int
        override var y: Int
        override var isAlive: Boolean
    }
}

fun RBuilder.cellView(x: Int, y: Int) = child(CellView::class) {
    attrs {
        this.x = x
        this.y = y
        this.isAlive = false
    }
}
