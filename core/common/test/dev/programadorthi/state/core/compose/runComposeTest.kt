package dev.programadorthi.state.core.compose

import androidx.compose.runtime.BroadcastFrameClock
import androidx.compose.runtime.Composition
import androidx.compose.runtime.Recomposer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
internal fun runComposeTest(
    content: TestScope.(Composition, () -> Unit) -> Unit
) = runTest {
    val job = Job()
    val clock = BroadcastFrameClock()
    val scope = CoroutineScope(coroutineContext + job + clock)
    val recomposer = Recomposer(scope.coroutineContext)
    val runner =
        scope.launch {
            recomposer.runRecomposeAndApplyChanges()
        }
    val composition = Composition(TestApplier(), recomposer)
    val invalidate: () -> Unit = {
        advanceTimeBy(99)
        clock.sendFrame(0L)
    }
    try {
        content(composition, invalidate)
    } finally {
        runner.cancel()
        recomposer.close()
        job.cancel()
    }
}