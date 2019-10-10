package lt.petuska.game.of.life.mpp.ui.presenter

import lt.petuska.game.of.life.mpp.game.GameEngine
import lt.petuska.game.of.life.mpp.redux.AppAction
import lt.petuska.game.of.life.mpp.ui.model.ControlModel
import lt.petuska.game.of.life.mpp.ui.view.ControlView

class ControlPresenter(override val view: ControlView) : Presenter<ControlModel>() {
    val minFps = 1
    val maxFps = 30
    override val model = object : ControlModel {
        override var universeWidth = appState.universeWidth
        override var universeHeight = appState.universeHeight
        override var fps = appState.fps
        override var isTimeRunning = appState.isTimeRunning

    }

    override fun onStateChange() {
        appState.run {
            if (model.universeWidth != universeWidth || model.universeHeight != universeHeight) {
                model.universeWidth = universeWidth
                model.universeHeight = universeHeight
                view.onUniverseDimensionsChange(model.universeWidth, model.universeHeight)
            }
            if (fps != model.fps) {
                model.fps = fps
                view.onFpsChange(model.fps)
            }
            if (model.isTimeRunning != isTimeRunning) {
                model.isTimeRunning = isTimeRunning
                view.onTimeStateChange(model.isTimeRunning)
            }
        }
    }

    fun handleResetResize(universeWidth: Int, universeHeight: Int) {
        dispatch(AppAction.SetTimeState(false))
        GameEngine.reset()
        if (universeWidth != appState.universeWidth || universeHeight != appState.universeHeight) {
            dispatch(AppAction.ResizeUniverse(universeWidth, universeHeight))
        }
    }

    fun handleTimeToggle() {
        dispatch(AppAction.ToggleTimeState)
    }

    fun handleTick() {
        GameEngine.tick()
    }

    fun handleFpsChange(fps: Int) {
        dispatch(AppAction.SetFPS(fps))
    }
}