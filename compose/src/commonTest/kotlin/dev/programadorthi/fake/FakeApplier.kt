package dev.programadorthi.fake

import androidx.compose.runtime.AbstractApplier

internal object FakeApplier : AbstractApplier<Unit>(Unit) {
    override fun onClear() = Unit

    override fun insertBottomUp(index: Int, instance: Unit) = Unit

    override fun insertTopDown(index: Int, instance: Unit) = Unit

    override fun move(from: Int, to: Int, count: Int) = Unit

    override fun remove(index: Int, count: Int) = Unit
}