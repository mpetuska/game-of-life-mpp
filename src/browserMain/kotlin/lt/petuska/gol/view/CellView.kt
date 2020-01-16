package lt.petuska.gol.view

import lt.petuska.gol.presenter.CellPresenter
import lt.petuska.kvdom.core.node.VElement
import lt.petuska.kvdom.core.node.html.VButton

actual class CellView(x: Int, y: Int) : View, VButton() {
    actual override val presenter = CellPresenter(this, x, y)

    private val baseStyle = "padding: 0px; " +
            "height: 16px; " +
            "width: 16px; "

    actual fun showAlive(alive: Boolean) {
        style = baseStyle + "background-color: ${if (alive) "black" else "white"}; "
    }

    init {
        style = baseStyle + "background-color: white; "

        eventListeners["click"] = {
            presenter.handleClick()
        }
    }

    override fun onInit() {
        presenter.initialise()
    }
}

fun VElement<*>.cellView(x: Int, y: Int, builder: CellView.() -> Unit = {}) = CellView(x, y).also {
    it.builder()
    children.add(it)
}