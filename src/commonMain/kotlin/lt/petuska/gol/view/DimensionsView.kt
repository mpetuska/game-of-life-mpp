package lt.petuska.gol.view

import lt.petuska.gol.model.Dimensions
import lt.petuska.gol.presenter.DimensionsPresenter

expect class DimensionsView : View {
    override val presenter: DimensionsPresenter
    fun showDimensions(dimensions: Dimensions)
}