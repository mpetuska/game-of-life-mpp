package lt.petuska.gol.view

import lt.petuska.gol.presenter.TimeControlPresenter
import lt.petuska.kvdom.core.node.VElement
import lt.petuska.kvdom.core.node.VText
import lt.petuska.kvdom.core.node.html.VDiv
import lt.petuska.kvdom.core.node.text
import lt.petuska.kvdom.dsl.button

actual class TimeControlView : View, VDiv() {
    actual override val presenter = TimeControlPresenter(this)

    private lateinit var timeButtonText: VText

    private val spacing = "0.5em"

    init {
        attributes["id"] = "time-control"
        style = listOf(
            "display: flex",
            "flex-direction: row",
            "align-items: center",
            "justify-content: center",
            "margin: $spacing"
        ).joinToString("; ", postfix = ";")
        button {
            eventListeners["click"] = {
                presenter.handleTimeToggle()
            }
            timeButtonText = text("Start")
        }
        button {
            eventListeners["click"] = {
                presenter.handleTick()
            }
            text("Tick")
        }
    }

    actual fun showRunning(running: Boolean) {
        timeButtonText.text = if (running) "Stop" else "Start"
    }

    override fun onInit() {
        presenter.initialise()
    }
}

fun VElement<*>.timeControlView(builder: TimeControlView.() -> Unit = {}) =
    TimeControlView().also {
        it.builder()
        children.add(it)
    }