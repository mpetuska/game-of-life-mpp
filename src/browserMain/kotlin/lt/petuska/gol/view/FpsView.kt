package lt.petuska.gol.view

import lt.petuska.gol.game.Const
import lt.petuska.gol.presenter.FpsPresenter
import lt.petuska.gol.redux.AppConfigState
import lt.petuska.kvdom.core.node.VElement
import lt.petuska.kvdom.core.node.VText
import lt.petuska.kvdom.core.node.html.VDiv
import lt.petuska.kvdom.core.node.text
import lt.petuska.kvdom.dom.node.html.HTMLElement
import lt.petuska.kvdom.dsl.div
import lt.petuska.kvdom.dsl.htmlElement
import lt.petuska.kvdom.dsl.input
import lt.petuska.kvdom.dsl.label

actual class FpsView : View, VDiv() {
    actual override val presenter = FpsPresenter(this)
    private val spacing = "0.5em"
    private lateinit var fpsText: VText

    init {
        style = listOf(
            "display: flex",
            "flex-direction: row",
            "align-items: center",
            "justify-content: space-around",
            "margin: $spacing"
        ).joinToString("; ", postfix = ";")
        label {
            style = "margin-right: 8px;"
            text("FPS")
        }
        div {
            style = listOf(
                "display: flex",
                "align-items: center",
                "width: 100%"
            ).joinToString("; ", postfix = ";")
            input {
              style = "width: 100%;"
              attributes["type"] = "range"
              attributes["min"] = "${Const.minFps}"
              attributes["max"] = "${Const.maxFps}"
              value = "${AppConfigState().fps}"
              eventListeners["change"] = {
                it.target.value?.toIntOrNull()?.let { fps ->
                  presenter.handleFpsChange(fps)
                  println("Change: $fps")
                }
              }
            }
            htmlElement<HTMLElement>("span") {
              style = listOf(
                "border-width: 1px",
                "border-color: gray",
                "padding: 1px"
              ).joinToString("; ", postfix = ";")
              fpsText = text("${AppConfigState().fps}")
            }
        }
    }

    actual fun showFps(fps: Int) {
        fpsText.text = "$fps"
    }

    override fun onInit() {
        presenter.initialise()
    }
}

fun VElement<*>.fpsView(builder: FpsView.() -> Unit = {}) =
    FpsView().also {
        it.builder()
        children.add(it)
    }