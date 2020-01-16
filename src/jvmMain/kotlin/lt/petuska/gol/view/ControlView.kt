package lt.petuska.gol.view

import javafx.geometry.Pos
import lt.petuska.gol.presenter.ControlPresenter
import tornadofx.*

actual class ControlView : View, tornadofx.View() {
    actual override val presenter = ControlPresenter(this)

    override val root = vbox {
        add(DimensionsView())
        add(FpsView())
        add(TimeControlView())
        style {
            spacing = 8.px
            paddingTop = 8
            paddingBottom = 8
        }
        children.style {
            alignment = Pos.CENTER
            spacing = 8.px
        }
    }

    override fun onDelete() = destroy()

    init {
        presenter.initialise()
    }
}