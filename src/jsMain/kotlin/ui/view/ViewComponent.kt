package lt.petuska.game.of.life.mpp.ui.view

import lt.petuska.game.of.life.mpp.ui.model.Model
import lt.petuska.game.of.life.mpp.ui.presenter.Presenter
import react.RComponent

abstract class ViewComponent<M : Model>(props: M) : RComponent<M, M>(props) {
    abstract val presenter: Presenter<*>

    override fun componentWillMount() {
        setState(props)
    }

    override fun componentWillUnmount() {
        presenter.onDestroy()
    }
}