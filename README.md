# kotlin-state-manager
A multiplatform and extensible state manager. Its wrapper the managed value to deliver a better, easy and extensible way. It's like a Value class with powerS.

> The project is not a replacement for Coroutines Flow or Compose State. Your origin is from 2022 when Compose wasn't multiplatform.

## How it works
There are a lot of ways to use a Value Manager

### As a basic variable
```kotlin
class CounterViewModel {
    val counter = basicValueManager(initialValue = 0)
    val counterFlow = counter.asMutableStateFlow() // StateFlow version
    
    var value by basicValueManager(initialValue = 0) // Delegate property version available
}
```

### Updating it value
```kotlin
class CounterViewModel {
    fun increment() {
        anyValueManagerType.update { current -> current + 1 }
        anyValueManager = anyValueManager + 1 // update method as a Delegate property
        anyValueManager++ // same as previous
    }
}
```

### Collecting value changes
```kotlin
class CounterViewModel {
    fun listen() {
        anyValueManagerType.collect {
            // collect without suspend is available
        }
        
        coroutinesScope.launch {
            flowValueManagerType.collect {
                // suspend collect available in Flow
            }
        }
    }
}
```

### Inside Jetpack Compose
```kotlin
@Composable
fun HomeScreen() {
    val counter = remember { basicValueManager(initialValue = 0) }
    var counterState by remember { counter.asState() } // remember or rememberSaveable are available
    
    // Update and listen operations are the same
}
```

### Listening for errors
```kotlin
class CounterViewModel {
    val counter = basicValueManager(initialValue = 0)
    
    init {
        counter.onError {
            
        }
    }
}
```

### Listening for changes
```kotlin
class CounterViewModel {
    val counter = basicValueManager(initialValue = 0)
    
    init {
        counter.onChanged {
            
        }
    }
}
```

### Validations are supported
```kotlin
class PositiveValidator(
    override val message: (Int) -> String = { "Value $it should be positive" }
) : Validator<Int> {
    override fun isValid(value: Int): Boolean = value > 0
}

val counter = basicValueManager(initialValue = 0)

counter.addValidator(PositiveValidator())
// or
counter += PositiveValidator()

counter.onValidated {
    // Listen on each validation operation
}

// Put a value don't trigger validations
counter.value = -1
// Call validate() to trigger validations
counter.validate()

// Calling update always trigger validations and don't need call validate()
counter.update { -1 }

// Checking is valid
counter.isValid()

// Getting validators messages
counter.messages()
```

### State Restoration

#### Compose

```kotlin
val counter by rememberSaveableValueManager { ... }
```

Without remember function or using a class to manager, you need to pass a `SaveableStateRegistry`

```kotlin
class MyComposeViewModel(stateRegistry: SaveableStateRegistry) {
    private var counter by composeValueManager(0, stateRegistry = stateRegistry)
}
```

Checkout [MVIViewModel](https://github.com/programadorthi/kotlin-state-manager/blob/master/samples/compose/norris-facts/common/src/commonMain/kotlin/dev/programadorthi/common/mvi/MVIViewModel.kt) sample for more details

#### Android

```kotlin
class MyActivity : ComponentActivity {
    private var counter by androidValueManager(0)
}
```

```kotlin
class MyFragment : AndroidXFragment {
    private var counter by androidValueManager(0)
}
```

```kotlin
class MyViewModel(savedStateHandle: SavedStateHandle) : AndroidXViewModel {
    private var counter by androidValueManager(0, savedStateHandle = savedStateHandle)
}
```

Checkout [MainActivity](https://github.com/programadorthi/kotlin-state-manager/blob/master/samples/compose/norris-facts/android/src/main/java/dev/programadorthi/android/MainActivity.kt) sample for more details

### Do you prefer inheritance over composition?

```kotlin
class CounterValueManager : BaseValueManager<Int>(initialValue = 0) {
    // Now all operations is available here
}
```

### Samples

Samples folder have a mix of usage.

Close usage to real project [here](https://github.com/programadorthi/full-stack-kotlin/blob/main/domain-model/interactors/src/commonMain/kotlin/dev/programadorthi/full/stack/interactors/user/LoginInteractor.kt)
