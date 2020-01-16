package lt.petuska.gol.view

import lt.petuska.gol.presenter.Presenter

interface View {
    val presenter: Presenter
    fun destroy() {
        presenter.destroy()
    }
}