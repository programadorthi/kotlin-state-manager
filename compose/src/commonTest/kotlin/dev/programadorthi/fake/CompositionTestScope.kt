package dev.programadorthi.fake

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlin.test.DefaultAsserter.assertTrue
import kotlin.test.assertFalse

internal interface CompositionTestScope : CoroutineScope {
    /**
     * A scheduler used by [CoroutineScope]
     */
    public val testCoroutineScheduler: TestCoroutineScheduler

    /**
     * Compose a block using the mock view composer.
     */
    public fun compose(block: @Composable () -> Unit)

    /**
     * Advance the state which executes any pending compositions, if any. Returns true if
     * advancing resulted in changes being applied.
     */
    public fun advance(ignorePendingWork: Boolean = false): Boolean

    /**
     * Advance counting the number of time the recomposer ran.
     */
    public fun advanceCount(ignorePendingWork: Boolean = false): Long

    /**
     * Verify the composition is well-formed.
     */
    public fun verifyConsistent()
}