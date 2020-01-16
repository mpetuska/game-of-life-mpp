package lt.petuska.gol.gtk3

import gtk.FALSE
import gtk.GtkGrid
import gtk.TRUE
import gtk.gtk_grid_attach
import gtk.gtk_grid_get_column_homogeneous
import gtk.gtk_grid_get_column_spacing
import gtk.gtk_grid_get_row_homogeneous
import gtk.gtk_grid_get_row_spacing
import gtk.gtk_grid_new
import gtk.gtk_grid_set_column_homogeneous
import gtk.gtk_grid_set_row_homogeneous
import gtk.gtk_grid_set_row_spacing
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlin.math.max

open class Grid : Container() {
  override val cObj: CPointer<GtkGrid> = gtk_grid_new()!!.reinterpret()
  
  private var rows = 0
  private var columns = 0
  var rowSpacing: Int
    get() = gtk_grid_get_row_spacing(cObj.reinterpret()).toInt()
    set(value) {
      gtk_grid_set_row_spacing(cObj.reinterpret(), value.toUInt())
    }
  var columnSpacing: Int
    get() = gtk_grid_get_column_spacing(cObj.reinterpret()).toInt()
    set(value) {
      gtk_grid_set_row_spacing(cObj.reinterpret(), value.toUInt())
    }
  var rowHomogeneous: Boolean
    get() = gtk_grid_get_row_homogeneous(cObj.reinterpret()) == TRUE
    set(value) {
      gtk_grid_set_row_homogeneous(cObj.reinterpret(), if (value) TRUE else FALSE)
    }
  var columnHomogeneous: Boolean
    get() = gtk_grid_get_column_homogeneous(cObj.reinterpret()) == TRUE
    set(value) {
      gtk_grid_set_column_homogeneous(cObj.reinterpret(), if (value) TRUE else FALSE)
    }
  
  fun clear() {
//    for (row in 0 until rows) {
//      gtk_grid_remove_row(cObj.reinterpret(), row)
//    }
//    for (column in 0 until rows) {
//      gtk_grid_remove_column(cObj.reinterpret(), column)
//    }
    rows = 0
    columns = 0
    children.forEach(Widget::dispose)
    children.clear()
  }
  
  private val children = mutableSetOf<Widget>()
  fun attach(child: Widget, x: Int, y: Int) {
    rows = max(y, rows)
    columns = max(x, columns)
    gtk_grid_attach(cObj.reinterpret(), child.cObj.reinterpret(), x, y, 1, 1)
    children.add(child)
  }
}