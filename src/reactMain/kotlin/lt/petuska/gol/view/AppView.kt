package lt.petuska.gol.view

import kotlinx.css.TextAlign
import kotlinx.css.textAlign
import lt.petuska.gol.presenter.AppPresenter
import react.RBuilder
import react.dom.a
import styled.styledH1

actual class AppView(props: Model) : View, ViewComponent<Model>(props) {
    actual override val presenter = AppPresenter(this)

    actual fun showApp() {
    }

    override fun RBuilder.render() {
        styledH1 {
            css.textAlign = TextAlign.center
            a {
                attrs.href = presenter.wikiUrl
                +"Conway's Game of Life"
            }
        }
        universeView()
        controlView()
    }
}