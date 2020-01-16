package lt.petuska.gol.presenter

import lt.petuska.gol.view.View

abstract class Presenter {
    protected abstract val view: View

    abstract fun initialise()

    open fun destroy() {}
}