package lt.petuska.gol.view

import lt.petuska.gol.presenter.TimeControlPresenter

expect class TimeControlView : View {
    override val presenter: TimeControlPresenter
    fun showRunning(running: Boolean)
}