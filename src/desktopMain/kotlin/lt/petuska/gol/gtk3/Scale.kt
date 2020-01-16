package lt.petuska.gol.gtk3

import gtk.FALSE
import gtk.GtkPositionType
import gtk.TRUE
import gtk.gtk_scale_get_digits
import gtk.gtk_scale_get_draw_value
import gtk.gtk_scale_get_value_pos
import gtk.gtk_scale_set_digits
import gtk.gtk_scale_set_draw_value
import gtk.gtk_scale_set_value_pos
import kotlinx.cinterop.reinterpret

abstract class Scale : Range() {
  var drawValue: Boolean
    get() = gtk_scale_get_draw_value(cObj.reinterpret()) == TRUE
    set(value) = gtk_scale_set_draw_value(cObj.reinterpret(), if (value) TRUE else FALSE)
  var digits: Int
    get() = gtk_scale_get_digits(cObj.reinterpret())
    set(value) = gtk_scale_set_digits(cObj.reinterpret(), value)
  var valuePos: GtkPositionType
    get() = gtk_scale_get_value_pos(cObj.reinterpret())
    set(value) = gtk_scale_set_value_pos(cObj.reinterpret(), value)
}