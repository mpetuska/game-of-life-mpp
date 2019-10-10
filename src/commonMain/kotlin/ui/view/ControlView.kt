package lt.petuska.game.of.life.mpp.ui.view

import lt.petuska.game.of.life.mpp.ui.model.ControlModel

expect class ControlView : View<ControlModel> {
    fun onUniverseDimensionsChange(width: Int, height: Int)
    fun onFpsChange(newFps: Int)
    fun onTimeStateChange(timeRunning: Boolean)
}