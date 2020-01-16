package lt.petuska.gol.presenter

import lt.petuska.gol.game.Coordinates
import lt.petuska.gol.redux.UniverseAction
import lt.petuska.gol.redux.UniverseState
import lt.petuska.gol.redux.UniverseStore
import lt.petuska.gol.view.CellView

class CellPresenter(override val view: CellView, private val x: Int, private val y: Int) : Presenter() {
    private val subscription = UniverseStore.subscribeToCellState(Coordinates(x, y)) {
        view.showAlive(it)
    }
    
    override fun initialise() {
        view.showAlive(UniverseState().universe[x, y])
    }
    
    override fun destroy() {
        subscription()
    }
    
    fun handleClick() {
        UniverseStore.dispatch(UniverseAction.ToggleCellState(Coordinates(x, y)))
    }
}