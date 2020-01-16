package lt.petuska.gol.gtk3

import gtk.GtkButton
import gtk.gtk_button_get_label
import gtk.gtk_button_new
import gtk.gtk_button_set_label
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString

open class Button : Widget() {
  override val cObj: CPointer<GtkButton> = gtk_button_new()!!.reinterpret()
  var onClicked: (Button.() -> Unit)? = null
    set(value) {
      value?.let { connectSignal("clicked", it) }
      field = value
    }
  
  var label: String?
    get() = gtk_button_get_label(cObj)?.toKString()
    set(value) = gtk_button_set_label(cObj, value)
}