package lt.petuska.gol.gtk3

import gtk.gtk_range_get_value
import gtk.gtk_range_set_value
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.reinterpret

abstract class Range : Widget() {
  var value: Double
    get() = gtk_range_get_value(cObj.reinterpret())
    set(value) = gtk_range_set_value(cObj.reinterpret(), value)
  var onValueChanged: (Range.() -> Unit)? = null
    set(value) {
      value?.let { connectSignal("value-changed", it) }
      field = value
    }
  private var ref: StableRef<Range>? = null
}