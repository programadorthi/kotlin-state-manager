package dev.programadorthi.state.core

import android.os.Bundle

public interface AndroidValueManagerStateSaver<Original> {

    public fun save(value: Original): Bundle

    public fun restore(bundle: Bundle): Original?
}

public fun <Original> AndroidValueManagerStateSaver(
    save: (value: Original) -> Bundle,
    restore: (value: Bundle) -> Original?
): AndroidValueManagerStateSaver<Original> {
    return object : AndroidValueManagerStateSaver<Original> {
        override fun save(value: Original) = save.invoke(value)

        override fun restore(bundle: Bundle) = restore.invoke(bundle)
    }
}