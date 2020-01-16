package lt.petuska.gol.view

import react.RComponent

abstract class ViewComponent<M : Model>(props: M) : RComponent<M, M>(props), View {

    override fun componentWillMount() {
        setState(props)
        presenter.initialise()
    }

    override fun componentWillUnmount() {
        destroy()
    }
}