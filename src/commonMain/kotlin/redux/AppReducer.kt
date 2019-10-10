package lt.petuska.game.of.life.mpp.redux

val AppReducer: (AppState, Any) -> AppState = { state, action ->
    if (action is AppAction) {
        when (action) {
            is AppAction.ToggleTimeState -> state.copy(isTimeRunning = !state.isTimeRunning)
            is AppAction.SetTimeState -> state.copy(isTimeRunning = action.isRunning)
            is AppAction.SetFPS -> state.copy(fps = action.fps)
            is AppAction.ResizeUniverse -> state.run {
                state.copy(universeWidth = action.width, universeHeight = action.height)
            }
        }
    } else {
        state
    }
}