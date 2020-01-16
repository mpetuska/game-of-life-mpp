package lt.petuska.gol.gtk3

import gtk.GApplicationFlags
import gtk.G_APPLICATION_FLAGS_NONE
import gtk.GtkApplication
import gtk.g_application_run
import gtk.gtk_application_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toCStringArray

class Application(
  id: String,
  flags: GApplicationFlags = G_APPLICATION_FLAGS_NONE
) : Widget() {
  override val cObj: CPointer<GtkApplication> = gtk_application_new(id, flags)!!
  
  var onActivate: (Application.() -> Unit)? = null
    set(value) {
      value?.let { connectSignal("activate", it) }
      field = value
    }
  
  fun run(args: Array<String>? = null) =
    g_application_run(cObj.reinterpret(), args?.size ?: 0, memScoped { args?.toCStringArray(this) })
}