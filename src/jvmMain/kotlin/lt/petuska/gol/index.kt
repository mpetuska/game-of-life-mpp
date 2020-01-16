package lt.petuska.gol

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lt.petuska.gol.game.startEngine
import lt.petuska.gol.redux.AppConfigStore
import lt.petuska.gol.redux.UniverseStore
import lt.petuska.gol.view.AppView
import tornadofx.*

class MyApp : App(AppView::class)

actual fun main(args: Array<String>) {
  GlobalScope.launch(Dispatchers.Main) {
    startEngine(AppConfigStore, UniverseStore)
  }
  launch<MyApp>(args)
}