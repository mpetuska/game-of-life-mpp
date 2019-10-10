package lt.petuska.game.of.life.mpp.ui.presenter

import lt.petuska.game.of.life.mpp.game.GameEngine
import lt.petuska.game.of.life.mpp.ui.model.CellModel
import lt.petuska.game.of.life.mpp.ui.view.CellView

class CellPresenter(
    override val view: CellView,
    x: Int,
    y: Int
) : Presenter<CellModel>() {
    private val unsubscribe: () -> Unit = GameEngine.subscribeToCell(x, y) {
        if (model.isAlive != it.isAlive) {
            model.isAlive = it.isAlive
            view.showAlive(model.isAlive)
        }
    }
    override val model = object : CellModel {
        override var x = x
        override var y = y
        override var isAlive = false
    }

    fun handleClick() {
        GameEngine.toggleCell(model.x, model.y)
    }

    override fun onDestroy() {
        super.onDestroy()
        unsubscribe()
    }
}