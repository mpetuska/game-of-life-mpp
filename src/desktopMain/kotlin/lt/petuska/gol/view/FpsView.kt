package lt.petuska.gol.view

import gtk.GtkPositionType
import lt.petuska.gol.game.Const
import lt.petuska.gol.gtk3.HBox
import lt.petuska.gol.gtk3.HScale
import lt.petuska.gol.gtk3.Label
import lt.petuska.gol.presenter.FpsPresenter
import lt.petuska.gol.presenter.Presenter
import lt.petuska.gol.redux.AppConfigAction
import lt.petuska.gol.redux.AppConfigStore

actual class FpsView : View, HBox() {
  private val scale = HScale(Const.minFps.toDouble(), Const.maxFps.toDouble(), 1.0).also {
    it.drawValue = true
    it.digits = 0
    it.valuePos = GtkPositionType.GTK_POS_RIGHT
    it.onValueChanged = {
      println("NevValue")
      AppConfigStore.dispatch(AppConfigAction.SetFps(value.toInt()))
    }
    add(Label("FPS: "))
    add(it)
  }
  
  init {
    spacing = 1
  }
  
  actual override val presenter = FpsPresenter(this).also(Presenter::initialise)
  
  actual fun showFps(fps: Int) {
    scale.value = fps.toDouble()
  }
}