package lt.petuska.gol.view

import lt.petuska.gol.presenter.AppPresenter

expect class AppView : View {
    override val presenter: AppPresenter
    fun showApp()
}