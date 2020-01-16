package lt.petuska.gol.view

import lt.petuska.gol.presenter.ControlPresenter

expect class ControlView : View {
    override val presenter: ControlPresenter
}