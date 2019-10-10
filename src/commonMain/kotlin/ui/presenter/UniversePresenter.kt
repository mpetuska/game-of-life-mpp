package lt.petuska.game.of.life.mpp.ui.presenter

import lt.petuska.game.of.life.mpp.ui.model.UniverseModel
import lt.petuska.game.of.life.mpp.ui.view.UniverseView

class UniversePresenter(override val view: UniverseView) : Presenter<UniverseModel>() {
    override val model = object : UniverseModel {
        override var height = appState.universeHeight
        override var width = appState.universeWidth
    }

    override fun onStateChange() {
        if (appState.universeWidth != model.width || appState.universeHeight != model.height) {
            model.width = appState.universeWidth
            model.height = appState.universeHeight
            view.updateDimensions(model.width, model.height)
        }
    }
}