package lt.petuska.gol.gtk3

import gtk.GtkLabel
import gtk.gtk_label_get_text
import gtk.gtk_label_new
import gtk.gtk_label_set_text
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString

open class Label(text: String = "") : Widget() {
  override val cObj: CPointer<GtkLabel> = gtk_label_new(text)!!.reinterpret()
  
  
  var text: String?
    get() = gtk_label_get_text(cObj)?.toKString()
    set(value) = gtk_label_set_text(cObj, value)
}