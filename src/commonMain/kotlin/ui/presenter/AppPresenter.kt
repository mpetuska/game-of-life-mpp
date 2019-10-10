package lt.petuska.game.of.life.mpp.ui.presenter

import lt.petuska.game.of.life.mpp.game.GameEngine
import lt.petuska.game.of.life.mpp.ui.model.AppModel
import lt.petuska.game.of.life.mpp.ui.view.AppView

class AppPresenter(override val view: AppView) : Presenter<AppModel>() {
    override val model = object : AppModel {}

    val wikiUrl = "https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life"

    init {
        GameEngine
    }
}