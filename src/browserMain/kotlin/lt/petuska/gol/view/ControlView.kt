package lt.petuska.gol.view

import lt.petuska.gol.presenter.ControlPresenter
import lt.petuska.kvdom.core.node.VElement
import lt.petuska.kvdom.core.node.html.VDiv
import lt.petuska.kvdom.dsl.hr

actual class ControlView : View, VDiv() {
    actual override val presenter = ControlPresenter(this)
    private val spacing = "0.5em"

    init {
        attributes["id"] = "control"
        style = listOf(
            "width: fit-content",
            "border-color: black",
            "border-style: solid",
            "border-width: 1px",
            "margin: auto",
            "margin-top: $spacing"
        ).joinToString("; ", postfix = ";")
        dimensionsView()
        separator()
        fpsView()
        separator()
        timeControlView()
    }

    private fun VElement<*>.separator() = hr {
        style = listOf(
            "border-style: dashed",
            "border-width: 0.5px"
        ).joinToString("; ", postfix = ";")
    }

    override fun onInit() {
        presenter.initialise()
    }
}

fun VElement<*>.controlView(builder: ControlView.() -> Unit = {}) =
    ControlView().also {
        it.builder()
        children.add(it)
    }