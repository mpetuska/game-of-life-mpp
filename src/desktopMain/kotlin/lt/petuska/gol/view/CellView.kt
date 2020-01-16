package lt.petuska.gol.view

import gtk.GdkColor
import gtk.GtkStateType
import gtk.gdk_color_parse
import gtk.gtk_widget_modify_bg
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import lt.petuska.gol.gtk3.Button
import lt.petuska.gol.presenter.CellPresenter
import lt.petuska.gol.presenter.Presenter

actual class CellView(x: Int, y: Int) : View, Button() {
  init {
    setSize(10, 10)
    onClicked = {
      presenter.handleClick()
    }
  }
  
  actual override val presenter = CellPresenter(this, x, y).also(Presenter::initialise)
  
  actual fun showAlive(alive: Boolean) {
    memScoped {
      val color: CValuesRef<GdkColor> = alloc<GdkColor>().ptr
      gdk_color_parse(if (alive) "black" else "white", color)
      gtk_widget_modify_bg(cObj.reinterpret(), GtkStateType.GTK_STATE_NORMAL, color)
    }
  }
  
  override fun dispose() {
    super.dispose()
    destroy()
  }
}