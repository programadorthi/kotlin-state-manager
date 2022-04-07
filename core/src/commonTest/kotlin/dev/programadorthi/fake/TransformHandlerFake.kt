package dev.programadorthi.fake

import dev.programadorthi.core.handler.TransformHandler

internal class TransformHandlerFake : TransformHandler<Int> {
    var breakable: Throwable? = null
    var transformable: (Int) -> Int = { it }

    override fun transform(current: Int): Int {
        if (breakable != null) {
            throw breakable!!
        }
        return transformable.invoke(current)
    }
}