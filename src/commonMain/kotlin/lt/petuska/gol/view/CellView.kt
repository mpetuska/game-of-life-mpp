package lt.petuska.gol.view

import lt.petuska.gol.presenter.CellPresenter

expect class CellView : View {
    override val presenter: CellPresenter
    fun showAlive(alive: Boolean)
}