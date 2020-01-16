package lt.petuska.gol.view

import kotlinx.css.LinearDimension
import kotlinx.css.margin
import kotlinx.css.width
import lt.petuska.gol.model.Dimensions
import lt.petuska.gol.presenter.UniversePresenter
import react.RBuilder
import react.setState
import styled.css
import styled.styledDiv

actual class UniverseView(props: Props) : View, ViewComponent<UniverseView.Props>(props) {
    actual override val presenter = UniversePresenter(this)

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

    actual fun showUniverse(dimensions: Dimensions) = setState {
        this.width = dimensions.width
        this.height = dimensions.height
    }

    interface Props : Model {
        var width: Int
        var height: Int
    }
}

fun RBuilder.universeView() = child(UniverseView::class) {
    attrs {
      this.width = 0
      this.height = 0
    }
}