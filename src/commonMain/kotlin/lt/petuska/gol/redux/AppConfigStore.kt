package lt.petuska.gol.redux

import lt.petuska.gol.model.Dimensions

data class AppConfigState(
  val universeDimensions: Dimensions = Dimensions(15, 20),
  val fps: Int = 3,
  val running: Boolean = false
)

sealed class AppConfigAction {
  data class SetUniverseDimensions(val dimensions: Dimensions) : AppConfigAction()
  data class SetFps(val fps: Int) : AppConfigAction()
  data class SetRunning(val running: Boolean) : AppConfigAction()
  object ToggleRunning : AppConfigAction()
}

val AppConfigStore = AppConfigStoreImpl()

class AppConfigStoreImpl : ReduxStore<AppConfigState, AppConfigAction>(AppConfigState()) {
  override fun reduce(state: AppConfigState, action: Any): AppConfigState = when (val a = action as? AppConfigAction) {
    null -> state
    is AppConfigAction.SetUniverseDimensions -> state.copy(universeDimensions = a.dimensions)
    is AppConfigAction.SetFps -> state.copy(fps = a.fps)
    is AppConfigAction.SetRunning -> state.copy(running = a.running)
    AppConfigAction.ToggleRunning -> state.copy(running = !state.running)
  }
}