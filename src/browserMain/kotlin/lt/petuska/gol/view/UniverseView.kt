package lt.petuska.gol.view

import lt.petuska.gol.model.Dimensions
import lt.petuska.gol.presenter.UniversePresenter
import lt.petuska.kvdom.core.node.VElement
import lt.petuska.kvdom.core.node.html.VDiv
import lt.petuska.kvdom.dsl.div

actual class UniverseView : View, VDiv() {
    actual override val presenter = UniversePresenter(this)

    actual fun showUniverse(dimensions: Dimensions) {
        children.clear()
        for (y in 0 until dimensions.height) {
            div {
                for (x in 0 until dimensions.width) {
                    cellView(x, y)
                }
            }
        }
    }

    init {
        style = "margin: auto; " + "width: fit-content;"
    }

    override fun onInit() {
        presenter.initialise()
    }
}

fun VElement<*>.universeView(builder: UniverseView.() -> Unit = {}) =
    UniverseView().also {
        it.builder()
        children.add(it)
    }