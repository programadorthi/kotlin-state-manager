package dev.programadorthi.compose.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.programadorthi.compose.ComposeValueManager
import dev.programadorthi.compose.ComposeValueManagerImpl
import dev.programadorthi.core.handler.DefaultHandler
import dev.programadorthi.core.handler.ErrorHandler
import dev.programadorthi.core.handler.LifecycleHandler
import dev.programadorthi.core.handler.TransformHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

fun <T> composeValueManager(
    initialValue: T,
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    lifecycleHandler: LifecycleHandler<T> = DefaultHandler(),
    transformHandler: TransformHandler<T> = DefaultHandler(),
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Unconfined
): ComposeValueManager<T> = ComposeValueManagerImpl(
    initialValue = initialValue,
    errorHandler = errorHandler,
    lifecycleHandler = lifecycleHandler,
    transformHandler = transformHandler,
    coroutineDispatcher = coroutineDispatcher
)

@Composable
fun <T> rememberComposeValueManager(
    initialValue: T,
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    lifecycleHandler: LifecycleHandler<T> = DefaultHandler(),
    transformHandler: TransformHandler<T> = DefaultHandler(),
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Unconfined
): ComposeValueManager<T> = remember {
    composeValueManager(
        initialValue = initialValue,
        errorHandler = errorHandler,
        lifecycleHandler = lifecycleHandler,
        transformHandler = transformHandler,
        coroutineDispatcher = coroutineDispatcher
    )
}
