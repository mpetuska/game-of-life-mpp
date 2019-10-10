package lt.petuska.game.of.life.mpp.redux

sealed class AppAction {
    class ResizeUniverse(val width: Int, val height: Int) : AppAction()
    object ToggleTimeState : AppAction()
    class SetTimeState(val isRunning: Boolean) : AppAction()
    class SetFPS(val fps: Int) : AppAction()
}