package lt.petuska.gol.gtk3

import gtk.GtkEntry
import gtk.gtk_entry_get_max_length
import gtk.gtk_entry_get_text
import gtk.gtk_entry_new
import gtk.gtk_entry_set_max_length
import gtk.gtk_entry_set_text
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString

open class Entry : Widget() {
  override val cObj: CPointer<GtkEntry> = gtk_entry_new()!!.reinterpret()
  
  
  var text: String?
    get() = gtk_entry_get_text(cObj)?.toKString()
    set(value) = gtk_entry_set_text(cObj, value)
  var maxLength: Int
    get() = gtk_entry_get_max_length(cObj)
    set(value) = gtk_entry_set_max_length(cObj, value)
}