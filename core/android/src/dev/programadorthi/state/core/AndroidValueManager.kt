package dev.programadorthi.state.core

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import dev.programadorthi.state.core.action.ChangeAction
import dev.programadorthi.state.core.action.ErrorAction
import dev.programadorthi.state.core.extension.basicValueManager
import dev.programadorthi.state.core.validation.Validator
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private const val KEY = "dev.programadorthi.state.core.AndroidValueManagerState"

private fun String.compoundKey(): String = "$KEY:$this"

public typealias AndroidValueManager<T> = PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, T>>

public fun <T> androidValueManager(
    initialValue: T,
    stateRestorationKey: String,
    savedStateHandle: SavedStateHandle,
    saver: AndroidValueManagerStateSaver<T>? = null,
    validators: List<Validator<T>>? = null,
    changeHandler: ChangeAction<T>? = null,
    errorHandler: ErrorAction? = null,
): Lazy<BaseValueManager<T>> = lazy {
    val valueManager = basicValueManager(
        initialValue = initialValue,
        validators = validators,
        changeHandler = changeHandler,
        errorHandler = errorHandler,
    )
    val state = AndroidValueManagerState(
        valueManager = valueManager,
        stateRestorationKey = stateRestorationKey.compoundKey(),
        savedStateHandle = savedStateHandle,
        saver = saver,
    )
    state.valueManager
}

public fun <T> androidValueManager(
    initialValue: T,
    savedStateHandle: SavedStateHandle,
    saver: AndroidValueManagerStateSaver<T>? = null,
    validators: List<Validator<T>>? = null,
    changeHandler: ChangeAction<T>? = null,
    errorHandler: ErrorAction? = null,
): AndroidValueManager<T> = PropertyDelegateProvider { _, property ->
    object : ReadWriteProperty<Any?, T> {
        val valueManager by androidValueManager(
            initialValue = initialValue,
            stateRestorationKey = property.name,
            savedStateHandle = savedStateHandle,
            saver = saver,
            validators = validators,
            changeHandler = changeHandler,
            errorHandler = errorHandler
        )

        override fun getValue(thisRef: Any?, property: KProperty<*>): T = valueManager.value

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            valueManager.value = value
        }
    }
}

public fun <T> ComponentActivity.androidValueManager(
    initialValue: T,
    stateRestorationKey: String,
    saver: AndroidValueManagerStateSaver<T>? = null,
    validators: List<Validator<T>>? = null,
    changeHandler: ChangeAction<T>? = null,
    errorHandler: ErrorAction? = null,
): Lazy<BaseValueManager<T>> = ownerValueManager(
    initialValue = initialValue,
    stateRestorationKey = stateRestorationKey,
    saver = saver,
    validators = validators,
    changeHandler = changeHandler,
    errorHandler = errorHandler,
)

public fun <T> ComponentActivity.androidValueManager(
    initialValue: T,
    saver: AndroidValueManagerStateSaver<T>? = null,
    validators: List<Validator<T>>? = null,
    changeHandler: ChangeAction<T>? = null,
    errorHandler: ErrorAction? = null,
): AndroidValueManager<T> = PropertyDelegateProvider { _, property ->
    object : ReadWriteProperty<Any?, T> {
        val valueManager by ownerValueManager(
            initialValue = initialValue,
            stateRestorationKey = property.name,
            saver = saver,
            validators = validators,
            changeHandler = changeHandler,
            errorHandler = errorHandler,
        )

        override fun getValue(thisRef: Any?, property: KProperty<*>): T = valueManager.value

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            valueManager.value = value
        }
    }
}

public fun <T> Fragment.androidValueManager(
    initialValue: T,
    stateRestorationKey: String,
    saver: AndroidValueManagerStateSaver<T>? = null,
    validators: List<Validator<T>>? = null,
    changeHandler: ChangeAction<T>? = null,
    errorHandler: ErrorAction? = null,
): Lazy<BaseValueManager<T>> = ownerValueManager(
    initialValue = initialValue,
    stateRestorationKey = stateRestorationKey,
    saver = saver,
    validators = validators,
    changeHandler = changeHandler,
    errorHandler = errorHandler,
)

public fun <T> Fragment.androidValueManager(
    initialValue: T,
    saver: AndroidValueManagerStateSaver<T>? = null,
    validators: List<Validator<T>>? = null,
    changeHandler: ChangeAction<T>? = null,
    errorHandler: ErrorAction? = null,
): AndroidValueManager<T> = PropertyDelegateProvider { _, property ->
    object : ReadWriteProperty<Any?, T> {
        val valueManager by ownerValueManager(
            initialValue = initialValue,
            stateRestorationKey = property.name,
            saver = saver,
            validators = validators,
            changeHandler = changeHandler,
            errorHandler = errorHandler,
        )

        override fun getValue(thisRef: Any?, property: KProperty<*>): T = valueManager.value

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            valueManager.value = value
        }
    }
}

private fun <T> ViewModelStoreOwner.ownerValueManager(
    initialValue: T,
    stateRestorationKey: String,
    saver: AndroidValueManagerStateSaver<T>? = null,
    validators: List<Validator<T>>? = null,
    changeHandler: ChangeAction<T>? = null,
    errorHandler: ErrorAction? = null,
): Lazy<BaseValueManager<T>> = lazy {
    val valueManager = basicValueManager(
        initialValue = initialValue,
        validators = validators,
        changeHandler = changeHandler,
        errorHandler = errorHandler,
    )
    val key = stateRestorationKey.compoundKey()
    val provider = ViewModelProvider(
        owner = this,
        factory = AndroidValueManagerStateFactory(
            stateRestorationKey = key,
            valueManager = valueManager,
            saver = saver,
        )
    )
    val state = provider[key, AndroidValueManagerState::class.java]
    @Suppress("UNCHECKED_CAST")
    state as AndroidValueManagerState<T>
    state.valueManager
}
