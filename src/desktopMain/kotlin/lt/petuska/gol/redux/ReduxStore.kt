package lt.petuska.gol.redux

import org.reduxkotlin.StoreSubscription
import org.reduxkotlin.createStore

actual abstract class ReduxStore<S, A : Any> actual constructor(initialState: S) {
  private val store = createStore(::reduce, initialState)
  protected actual val state: S
    get() = store.state
  
  private val statefulSubscriptions = mutableSetOf<StatefulSubscriber<S>>()
  actual fun subscribe(subscriber: StatefulSubscriber<S>): StoreSubscription {
    statefulSubscriptions.add(subscriber)
    return {
      statefulSubscriptions.remove(subscriber)
    }
  }
  
  actual fun dispatch(action: A) {
    store.dispatch(action)
  }
  
  init {
    store.subscribe.invoke {
      val state = store.state
      statefulSubscriptions.forEach { it(state) }
    }
  }
  
  actual abstract fun reduce(state: S, action: Any): S
}