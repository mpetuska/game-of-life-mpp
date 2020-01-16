package lt.petuska.gol.view

import lt.petuska.gol.model.Dimensions
import lt.petuska.gol.presenter.UniversePresenter

expect class UniverseView : View {
    override val presenter: UniversePresenter
    fun showUniverse(dimensions: Dimensions)
}