package lt.petuska.gol.redux

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscription
import org.reduxkotlin.createStore

actual abstract class ReduxStore<S, A : Any> actual constructor(initialState: S) {
  private val reduxDispatcher = newSingleThreadContext("${this::class.simpleName}-redux")
  private var subscriptions = mutableSetOf<(S) -> Unit>()
  private val store: Store<S> = runBlocking(reduxDispatcher) {
    createStore(::reduce, initialState).also {
      it.subscribe {
        val state = it.state
        GlobalScope.launch(Dispatchers.JavaFx) {
          subscriptions.forEach { s -> s(state) }
        }
      }
    }
  }
  
  actual fun dispatch(action: A) {
    GlobalScope.launch(reduxDispatcher) { store.dispatch(action) }
  }
  
  actual fun subscribe(subscriber: (S) -> Unit): StoreSubscription {
    subscriptions.add(subscriber)
    return {
      subscriptions.remove(subscriber)
    }
  }
  
  protected actual val state: S
    get() = runBlocking(reduxDispatcher) {
      store.state
    }
  
  actual abstract fun reduce(state: S, action: Any): S
}