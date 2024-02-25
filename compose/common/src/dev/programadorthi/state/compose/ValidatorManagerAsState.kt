package dev.programadorthi.state.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import dev.programadorthi.state.core.validation.ValidatorManager

public fun <T> ValidatorManager<T>.asValidatorState(): ValidatorManagerState =
    ValidatorManagerStateImpl(this)

@Composable
public fun <T> rememberValidatorState(
    validatorManager: () -> ValidatorManager<T>
): ValidatorManagerState = remember {
    validatorManager().asValidatorState()
}

@Composable
public fun <T> rememberSaveableValidatorState(
    validatorManager: () -> ValidatorManager<T>
): ValidatorManagerState {
    val managerState = remember { ValidatorManagerStateImpl(validatorManager()) }
    return rememberSaveable(
        init = { managerState },
        saver = mapSaver(
            save = { it.toSave() },
            restore = { managerState.toRestore(it) }
        )
    )
}
