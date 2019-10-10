package lt.petuska.game.of.life.mpp.ui.view

import lt.petuska.game.of.life.mpp.ui.model.CellModel

expect class CellView : View<CellModel> {
    fun showAlive(isAlive: Boolean)
}