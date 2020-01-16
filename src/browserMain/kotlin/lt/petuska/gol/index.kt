package lt.petuska.gol

import lt.petuska.gol.game.startEngine
import lt.petuska.gol.redux.AppConfigStore
import lt.petuska.gol.redux.UniverseStore
import lt.petuska.gol.view.AppView
import lt.petuska.kvdom.dom.document
import lt.petuska.kvdom.dom.node.setInterval
import lt.petuska.kvdom.dom.window

actual fun main(args: Array<String>) {
  val dRoot = document.getElementById("root")!!
  val app = AppView()
  window.setInterval(100) {
    app.patch(dRoot)
  }
  startEngine(AppConfigStore, UniverseStore)
}