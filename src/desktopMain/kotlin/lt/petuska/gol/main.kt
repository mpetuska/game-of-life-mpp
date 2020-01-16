package lt.petuska.gol

import lt.petuska.gol.game.startEngine
import lt.petuska.gol.gtk3.Application
import lt.petuska.gol.redux.AppConfigStore
import lt.petuska.gol.redux.UniverseStore
import lt.petuska.gol.view.AppView
import kotlin.system.exitProcess

actual fun main(args: Array<String>): Unit {
  val app = Application("lt.petuska.gol")
  app.onActivate = {
    startEngine(AppConfigStore, UniverseStore)
    AppView(this)
  }
  val status = app.run(args)
  exitProcess(status)
}