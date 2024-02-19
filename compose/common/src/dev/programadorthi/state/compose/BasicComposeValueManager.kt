package dev.programadorthi.state.compose

import androidx.compose.runtime.SnapshotMutationPolicy

public class BasicComposeValueManager<T>(
    initialValue: T,
    policy: SnapshotMutationPolicy<T>,
) : BaseComposeValueManager<T>(initialValue, policy)