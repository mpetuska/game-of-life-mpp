package lt.petuska.gol.gtk3

import gtk.GtkHScale
import gtk.gtk_hscale_new_with_range
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

class HScale(min: Double, max: Double, step: Double) : Scale() {
  override val cObj: CPointer<GtkHScale> = gtk_hscale_new_with_range(min, max, step)!!.reinterpret()
}