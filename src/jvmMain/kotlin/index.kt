package lt.petuska.game.of.life.mpp

import lt.petuska.game.of.life.mpp.ui.view.AppView
import tornadofx.App
import tornadofx.launch

class MyApp : App(AppView::class)

actual fun main(args: Array<String>) {
    launch<MyApp>(args)
}