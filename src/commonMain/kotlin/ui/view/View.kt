package lt.petuska.game.of.life.mpp.ui.view

import lt.petuska.game.of.life.mpp.ui.model.Model
import lt.petuska.game.of.life.mpp.ui.presenter.Presenter

interface View<M : Model> {
    val presenter: Presenter<M>
}