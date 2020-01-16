package lt.petuska.gol.view

import lt.petuska.gol.gtk3.Button
import lt.petuska.gol.gtk3.Entry
import lt.petuska.gol.gtk3.HBox
import lt.petuska.gol.gtk3.Label
import lt.petuska.gol.model.Dimensions
import lt.petuska.gol.presenter.DimensionsPresenter
import lt.petuska.gol.presenter.Presenter

actual class DimensionsView : View, HBox() {
  private val widthEntry = Entry().also {
    it.maxLength = 2
  }
  private val heightEntry = Entry().also {
    it.maxLength = 2
  }
  private val btn = Button().also {
    it.label = "Reset/Resize"
    it.onClicked = {
      widthEntry.text?.toIntOrNull()?.let { width ->
        heightEntry.text?.toIntOrNull()?.let { height ->
          presenter.handleDimensionsChange(Dimensions(width, height))
        }
      }
    }
  }
  
  init {
    add(Label("Width: "))
    add(widthEntry)
    add(Label("Height: "))
    add(heightEntry)
    add(btn)
    spacing = 1
  }
  
  actual override val presenter = DimensionsPresenter(this).also(Presenter::initialise)
  
  actual fun showDimensions(dimensions: Dimensions) {
    widthEntry.text = "${dimensions.width}"
    heightEntry.text = "${dimensions.height}"
  }
}