package lt.petuska.game.of.life.mpp.ui.presenter

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lt.petuska.game.of.life.mpp.redux.AppAction
import lt.petuska.game.of.life.mpp.redux.AppStore
import lt.petuska.game.of.life.mpp.ui.model.Model
import lt.petuska.game.of.life.mpp.ui.view.View

abstract class Presenter<M : Model> {
    protected abstract val view: View<M>
    protected abstract val model: M
    protected val appState
        get() = AppStore.getState()

    private val unsubscribe = AppStore.subscribe {
        onStateChange()
    }

    init {
        GlobalScope.launch {
            delay(100)
            onStateChange()
        }
    }

    protected open fun onStateChange() {}

    open fun onDestroy() {
        unsubscribe()
    }

    protected fun dispatch(appAction: AppAction) {
        AppStore.dispatch(appAction)
    }
}