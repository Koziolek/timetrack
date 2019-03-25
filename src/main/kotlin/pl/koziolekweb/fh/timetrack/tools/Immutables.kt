package pl.koziolekweb.fh.timetrack.tools

class ImmutableList<T>(private val inner:List<T>) : List<T> by inner

fun <T> List<T>.toImmutableList(): List<T> {
    if (this is com.google.common.collect.ImmutableList<T>) {
        return this
    } else {
        return ImmutableList(this)
    }
}


