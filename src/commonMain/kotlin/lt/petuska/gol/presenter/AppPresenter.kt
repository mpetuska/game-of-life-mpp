package lt.petuska.gol.presenter

import lt.petuska.gol.view.AppView

class AppPresenter(override val view: AppView) : Presenter() {
    val wikiUrl = "https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life"

    override fun initialise() {
        view.showApp()
    }
}