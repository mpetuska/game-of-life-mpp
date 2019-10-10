package lt.petuska.game.of.life.mpp.ui.view

import kotlinx.css.TextAlign
import kotlinx.css.textAlign
import lt.petuska.game.of.life.mpp.ui.model.AppModel
import lt.petuska.game.of.life.mpp.ui.presenter.AppPresenter
import react.RBuilder
import react.dom.a
import styled.styledH1

actual class AppView(props: Props) : View<AppModel>, ViewComponent<AppView.Props>(props) {
    override val presenter = AppPresenter(this)

    override fun RBuilder.render() {
        styledH1 {
            css.textAlign = TextAlign.center
            a(presenter.wikiUrl) {
                +"Conway's Game of Life"
            }
        }
        universeView()
        controlView()
    }


    interface Props : AppModel
}

fun RBuilder.appView() = child(AppView::class) { attrs {} }