package lt.petuska.gol.gtk3

import gtk.G_APPLICATION_FLAGS_NONE
import gtk.g_signal_connect_data
import gtk.gtk_widget_destroy
import gtk.gtk_widget_set_size_request
import gtk.gtk_widget_show
import gtk.gtk_widget_show_all
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CStructVar
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.staticCFunction
import kotlin.collections.set


abstract class Widget {
  abstract val cObj: CPointer<out CStructVar>
  fun show() = gtk_widget_show(cObj.reinterpret())
  
  fun showAll() = gtk_widget_show_all(cObj.reinterpret())
  
  private val callbacks = mutableMapOf<String, Widget.() -> Unit>()
  private val callbackDataRefs = mutableMapOf<String, StableRef<CallbackData<Widget>>>()
  
  @Suppress("UNCHECKED_CAST")
  protected fun <W : Widget> connectSignal(
    signal: String,
    handler: W.() -> Unit
  ) {
    callbackDataRefs[signal]?.dispose()
    val ref = StableRef.create(CallbackData(signal, this))
    callbackDataRefs[signal] = ref
    callbacks[signal] = handler as Widget.() -> Unit
    g_signal_connect_data(
      cObj, signal, staticCFunction<COpaquePointer, COpaquePointer, Unit> { _, r ->
        r.asStableRef<CallbackData<W>>().get().let {
          it.obj.callbacks[it.signal]?.invoke(it.obj)
        }
      }.reinterpret(), ref.asCPointer(), null,
      G_APPLICATION_FLAGS_NONE
    )
  }
  
  fun setSize(width: Int, height: Int) {
    gtk_widget_set_size_request(cObj.reinterpret(), width, height)
  }
  
  open fun dispose() {
    callbackDataRefs.values.forEach {
      it.dispose()
    }
    gtk_widget_destroy(cObj.reinterpret())
  }
}