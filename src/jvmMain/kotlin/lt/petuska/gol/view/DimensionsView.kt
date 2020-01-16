package lt.petuska.gol.view

import javafx.beans.property.SimpleIntegerProperty
import javafx.util.converter.NumberStringConverter
import lt.petuska.gol.model.Dimensions
import lt.petuska.gol.presenter.DimensionsPresenter
import tornadofx.button
import tornadofx.hbox
import tornadofx.label
import tornadofx.textfield

actual class DimensionsView : View, tornadofx.View() {
    actual override val presenter = DimensionsPresenter(this)

    actual fun showDimensions(dimensions: Dimensions) {
        universeWidth.value = dimensions.width
        universeHeight.value = dimensions.height
    }

    private var universeWidth = SimpleIntegerProperty(0)
    private var universeHeight = SimpleIntegerProperty(0)

    override val root = hbox {
        label("Width")
        textfield {
            prefWidth = 50.0
            textProperty().bindBidirectional(universeWidth, NumberStringConverter())
        }
        label("Height")
        textfield {
            prefWidth = 50.0
            textProperty().bindBidirectional(universeHeight, NumberStringConverter())
        }
        button("Resize & Reset") {
            setOnMouseClicked {
                presenter.handleDimensionsChange(Dimensions(universeWidth.value, universeHeight.value))
            }
        }
    }

    override fun onDelete() = destroy()

    init {
        presenter.initialise()
    }
}