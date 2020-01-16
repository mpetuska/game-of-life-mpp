package lt.petuska.gol.gtk3

import gtk.gulong

data class CallbackData<T : Any>(val signal: String, val obj: T, var id: gulong? = null)