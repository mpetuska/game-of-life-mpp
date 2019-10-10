package lt.petuska.game.of.life.mpp

import lt.petuska.game.of.life.mpp.ui.view.appView
import react.dom.render
import kotlin.browser.document
import kotlin.browser.window

actual fun main(args: Array<String>) {
    window.onload = {
        render(document.getElementById("root")) {
            appView()
        }
    }
}