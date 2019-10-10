package lt.petuska.game.of.life.mpp.redux

import org.reduxkotlin.createStore

@Suppress("UNCHECKED_CAST")
val AppStore = createStore(
    AppReducer,
    AppState()
)
