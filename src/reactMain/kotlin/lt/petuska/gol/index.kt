package lt.petuska.gol

import lt.petuska.gol.game.startEngine
import lt.petuska.gol.redux.AppConfigStore
import lt.petuska.gol.redux.UniverseStore
import lt.petuska.gol.view.AppView
import react.dom.render
import kotlin.browser.document

actual fun main(args: Array<String>) {
  render(document.getElementById("root")) {
    child(AppView::class) {}
  }
  startEngine(AppConfigStore, UniverseStore)
}