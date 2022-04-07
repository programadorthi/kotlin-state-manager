package dev.programadorthi.compose.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.programadorthi.compose.ComposeBasicValueManager
import dev.programadorthi.compose.ComposeBasicValueManagerImpl
import dev.programadorthi.compose.ComposeFlowValueManager
import dev.programadorthi.compose.ComposeFlowValueManagerImpl
import dev.programadorthi.core.handler.DefaultHandler
import dev.programadorthi.core.handler.ErrorHandler
import dev.programadorthi.core.handler.LifecycleHandler
import dev.programadorthi.core.handler.TransformHandler

fun <T> composeBasicValueManager(
    initialValue: T,
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    lifecycleHandler: LifecycleHandler<T> = DefaultHandler(),
    transformHandler: TransformHandler<T> = DefaultHandler()
): ComposeBasicValueManager<T> = ComposeBasicValueManagerImpl(
    initialValue = initialValue,
    errorHandler = errorHandler,
    lifecycleHandler = lifecycleHandler,
    transformHandler = transformHandler,
)

fun <T> composeFlowValueManager(
    initialValue: T,
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    lifecycleHandler: LifecycleHandler<T> = DefaultHandler(),
    transformHandler: TransformHandler<T> = DefaultHandler()
): ComposeFlowValueManager<T> = ComposeFlowValueManagerImpl(
    initialValue = initialValue,
    errorHandler = errorHandler,
    lifecycleHandler = lifecycleHandler,
    transformHandler = transformHandler,
)

@Composable
fun <T> rememberComposeBasicValueManager(
    initialValue: T,
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    lifecycleHandler: LifecycleHandler<T> = DefaultHandler(),
    transformHandler: TransformHandler<T> = DefaultHandler()
): ComposeBasicValueManager<T> = remember {
    ComposeBasicValueManagerImpl(
        initialValue = initialValue,
        errorHandler = errorHandler,
        lifecycleHandler = lifecycleHandler,
        transformHandler = transformHandler,
    )
}

@Composable
fun <T> rememberComposeFlowValueManager(
    initialValue: T,
    errorHandler: ErrorHandler = DefaultHandler<T>(),
    lifecycleHandler: LifecycleHandler<T> = DefaultHandler(),
    transformHandler: TransformHandler<T> = DefaultHandler()
): ComposeFlowValueManager<T> = remember {
    ComposeFlowValueManagerImpl(
        initialValue = initialValue,
        errorHandler = errorHandler,
        lifecycleHandler = lifecycleHandler,
        transformHandler = transformHandler,
    )
}
