package lt.petuska.gol.view

import lt.petuska.gol.presenter.FpsPresenter

expect class FpsView : View {
    override val presenter: FpsPresenter
    fun showFps(fps: Int)
}