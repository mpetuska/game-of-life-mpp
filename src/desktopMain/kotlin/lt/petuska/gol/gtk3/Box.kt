package lt.petuska.gol.gtk3

import gtk.gtk_box_get_spacing
import gtk.gtk_box_set_spacing
import kotlinx.cinterop.reinterpret


abstract class Box : Container() {
  var spacing: Int
    get() = gtk_box_get_spacing(cObj.reinterpret())
    set(value) {
      gtk_box_set_spacing(cObj.reinterpret(), value)
    }
}