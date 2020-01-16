package lt.petuska.gol.gtk3

import gtk.GtkWindow
import gtk.gtk_application_window_new
import gtk.gtk_window_get_title
import gtk.gtk_window_set_title
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString

open class Window(app: Application) : Container() {
  override val cObj: CPointer<GtkWindow> = gtk_application_window_new(app.cObj)!!.reinterpret()
  
  var title: String?
    get() = gtk_window_get_title(cObj)?.toKString()
    set(value) {
      gtk_window_set_title(cObj, value)
    }
}