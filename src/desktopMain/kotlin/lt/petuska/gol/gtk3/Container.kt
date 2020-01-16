package lt.petuska.gol.gtk3

import gtk.gtk_container_add
import kotlinx.cinterop.reinterpret


abstract class Container : Widget() {
  fun add(child: Widget) {
    gtk_container_add(cObj.reinterpret(), child.cObj.reinterpret())
  }
}