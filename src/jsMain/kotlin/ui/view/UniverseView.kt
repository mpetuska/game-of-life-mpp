package lt.petuska.game.of.life.mpp.ui.view

import kotlinx.css.LinearDimension
import kotlinx.css.margin
import kotlinx.css.width
import lt.petuska.game.of.life.mpp.redux.AppStore
import lt.petuska.game.of.life.mpp.ui.model.UniverseModel
import lt.petuska.game.of.life.mpp.ui.presenter.UniversePresenter
import react.RBuilder
import react.setState
import styled.css
import styled.styledDiv

actual class UniverseView(props: Props) : View<UniverseModel>, ViewComponent<UniverseView.Props>(props) {
    override val presenter = UniversePresenter(this)

    override fun RBuilder.render() {
        styledDiv {
            css {
                margin = "auto"
                width = LinearDimension.fitContent
            }
            for (y in 0 until state.height) {
                styledDiv {
                    for (x in 0 until state.width) {
                        cellView(x, y)
                    }
                }
            }
        }
    }

    actual fun updateDimensions(width: Int, height: Int) = setState {
        this.width = width
        this.height = height
    }


    interface Props : UniverseModel {
        override var width: Int
        override var height: Int
    }
}

fun RBuilder.universeView() = child(UniverseView::class) {
    attrs {
        this.width = AppStore.state.universeWidth
        this.height = AppStore.state.universeHeight
    }
}
