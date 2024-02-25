package dev.programadorthi.state.compose.fake

import androidx.compose.runtime.saveable.SaveableStateRegistry

internal class FakeEntry(
    val key: String,
    val valueProvider: () -> Any?,
) : SaveableStateRegistry.Entry {
    var unregistered: Boolean = false
        private set

    override fun unregister() {
        check(!unregistered) {
            "Entry is already unregistered"
        }
        unregistered = true
    }
}