/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.programadorthi.fake

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.ControlledComposition
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.snapshots.Snapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext

@OptIn(InternalComposeApi::class, ExperimentalCoroutinesApi::class)
internal fun compositionTest(
    block: suspend CompositionTestScope.() -> Unit
): TestResult = runTest {
    withContext(testMonotonicFrameClock(this)) {
        // Start the recomposer
        val recomposer = Recomposer(coroutineContext)
        launch { recomposer.runRecomposeAndApplyChanges() }
        testScheduler.runCurrent()

        // Create a test scope for the test using the test scope passed in by runTest
        val scope = object : CompositionTestScope, CoroutineScope by this@runTest {
            var composed = false
            var composition: Composition? = null

            override val testCoroutineScheduler: TestCoroutineScheduler
                get() = this@runTest.testScheduler

            override fun compose(block: @Composable () -> Unit) {
                check(!composed) { "Compose should only be called once" }
                composed = true
                val composition = Composition(FakeApplier, recomposer)
                this.composition = composition
                composition.setContent(block)
            }

            override fun advanceCount(ignorePendingWork: Boolean): Long {
                val changeCount = recomposer.changeCount
                Snapshot.sendApplyNotifications()
                if (recomposer.hasPendingWork) {
                    testScheduler.advanceTimeBy(5_000)
                    check(ignorePendingWork || !recomposer.hasPendingWork) {
                        "Potentially infinite recomposition, still recomposing after advancing"
                    }
                }
                return recomposer.changeCount - changeCount
            }

            override fun advance(ignorePendingWork: Boolean) = advanceCount(ignorePendingWork) != 0L

            override fun verifyConsistent() {
                (composition as? ControlledComposition)?.verifyConsistent()
            }
        }
        scope.block()
        scope.composition?.dispose()
        recomposer.cancel()
        recomposer.join()
    }
}