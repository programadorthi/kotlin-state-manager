# kotlin-state-manager
A multiplatform and extensible state manager. Its wrapper the managed value to deliver a better, easy and extensible way. It's like a Value class with powerS.

## How it works
There are a lot of ways to use a Value/State Manager

### As a basic variable
```kotlin
class CounterViewModel {
    val counter = basicValueManager(initialValue = 0) // Basic value manager is already compose State
    val counterFlow = counter.asMutableStateFlow() // StateFlow version
    
    var value by basicValueManager(initialValue = 0) // Delegate property version available by compose getValue and setValue
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
            // collect without suspend is available in all types
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
    val counter = basicValueManager(initialValue = 0)
    var counterRemembered by remember { counter.asState() }
    
    // Update and listen operations are same
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

### Prefer inheritance over composition?

```kotlin
class CounterValueManager : BaseValueManager<Int>(initialValue = 0) {
    // Now all operations is available here
}
```


