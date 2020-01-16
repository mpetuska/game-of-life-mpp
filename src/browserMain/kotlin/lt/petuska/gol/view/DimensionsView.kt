package lt.petuska.gol.view

import lt.petuska.gol.model.Dimensions
import lt.petuska.gol.presenter.DimensionsPresenter
import lt.petuska.kvdom.core.node.VElement
import lt.petuska.kvdom.core.node.html.VDiv
import lt.petuska.kvdom.core.node.html.VInput
import lt.petuska.kvdom.core.node.text
import lt.petuska.kvdom.dsl.button
import lt.petuska.kvdom.dsl.div
import lt.petuska.kvdom.dsl.input
import lt.petuska.kvdom.dsl.label

actual class DimensionsView : View, VDiv() {
    actual override val presenter = DimensionsPresenter(this)
    private var widthInput: VInput
    private var heightInput: VInput
    private val spacing = "0.5em"

    init {
        attributes["id"] = "dimension-control"
        style = listOf(
            "display: flex",
            "flex-direction: row",
            "align-items: center",
            "justify-content: space-around",
            "margin: $spacing"
        ).joinToString("; ", postfix = ";")
        widthInput = dimensionInput(0, "Width")
        heightInput = dimensionInput(0, "Height")
        button {
            style = "margin: $spacing;"
            eventListeners["click"] = {
                presenter.handleDimensionsChange(
                    Dimensions(
                        widthInput.attributes["value"]!!.toInt(),
                        heightInput.attributes["value"]!!.toInt()
                    )
                )
            }
            text("Resize/Reset")
        }
    }

    actual fun showDimensions(dimensions: Dimensions) {
        widthInput.value = "${dimensions.width}"
        heightInput.value = "${dimensions.height}"
    }

    private fun VElement<*>.dimensionInput(initial: Int, label: String): VInput {
        lateinit var ret: VInput
        div {
            style = "margin: 0.25em"
            label {
                style = "margin-right: 8px; "
                text(label)
            }
            ret = input {
                style = "width: 32px;"
                value = "$initial"
                eventListeners["change"] = {
                    it.target.value?.let { newValue ->
                        value = newValue
                    }
                }
            }
        }
        return ret
    }

    override fun onInit() {
        presenter.initialise()
    }
}

fun VElement<*>.dimensionsView(builder: DimensionsView.() -> Unit = {}) =
    DimensionsView().also {
        it.builder()
        children.add(it)
    }