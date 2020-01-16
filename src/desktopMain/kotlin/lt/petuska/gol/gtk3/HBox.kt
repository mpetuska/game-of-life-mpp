package lt.petuska.gol.gtk3

import gtk.FALSE
import gtk.GtkHBox
import gtk.TRUE
import gtk.gtk_box_pack_start
import gtk.gtk_hbox_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

open class HBox : Box() {
  override val cObj: CPointer<GtkHBox> = gtk_hbox_new(FALSE, 1)!!.reinterpret()
  
  fun attach(child: Widget) {
    gtk_box_pack_start(cObj.reinterpret(), child.cObj.reinterpret(), TRUE, TRUE, 0)
  }
}