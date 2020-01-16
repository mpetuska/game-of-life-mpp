package lt.petuska.gol.view

import lt.petuska.gol.presenter.AppPresenter
import lt.petuska.kvdom.core.node.html.VDiv
import lt.petuska.kvdom.core.node.text
import lt.petuska.kvdom.dsl.a
import lt.petuska.kvdom.dsl.h1

actual class AppView : View, VDiv() {
    actual override val presenter = AppPresenter(this)

    actual fun showApp() {
        h1 {
            style = "text-align: center"
            a {
                href = presenter.wikiUrl
                text("Conway's Game of Life")
            }
        }
        universeView()
        controlView()
    }

    override fun onInit() {
        presenter.initialise()
    }
}