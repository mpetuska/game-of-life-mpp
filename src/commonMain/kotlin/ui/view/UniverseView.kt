package lt.petuska.game.of.life.mpp.ui.view

import lt.petuska.game.of.life.mpp.ui.model.UniverseModel

expect class UniverseView : View<UniverseModel> {
    fun updateDimensions(width: Int, height: Int = width)
}