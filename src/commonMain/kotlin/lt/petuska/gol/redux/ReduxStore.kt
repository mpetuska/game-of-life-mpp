package lt.petuska.gol.redux

import org.reduxkotlin.StoreSubscription

typealias StatefulSubscriber<S> = (S) -> Unit

expect abstract class ReduxStore<S, A : Any>(initialState: S) {
  protected val state: S
  fun subscribe(subscriber: StatefulSubscriber<S>): StoreSubscription
  fun dispatch(action: A)
  abstract fun reduce(state: S, action: Any): S
}