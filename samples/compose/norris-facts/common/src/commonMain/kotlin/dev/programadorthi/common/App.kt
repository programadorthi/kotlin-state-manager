package dev.programadorthi.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.programadorthi.common.api.NorrisApi
import dev.programadorthi.common.login.LoginScreen
import dev.programadorthi.common.state.CategoriesValueManager
import dev.programadorthi.common.state.FactsValueManager
import dev.programadorthi.common.state.State
import dev.programadorthi.state.compose.asState
import dev.programadorthi.state.core.extension.basicValueManager

@Composable
fun App() {
    var view by remember { mutableStateOf(0) }

    when (view) {
        1 -> LoginScreen()
        2 -> NorrisApp()
        else -> Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Button({
                view = 1
            }) {
                Text("Login sample")
            }
            Spacer(Modifier.height(20.dp))
            Button({
                view = 2
            }) {
                Text("Norris sample")
            }
        }
    }
}

@Composable
fun NorrisApp() {
    val api = remember { NorrisApi() }

    var selectedCategory by remember { basicValueManager(initialValue = "random").asState() }

    DisposableEffect(api) {
        onDispose {
            api.close()
        }
    }

    Column {
        Spacer(modifier = Modifier.height(16.dp))
        ComposeCategories(
            api = api,
            onSelectCategory = { category ->
                selectedCategory = category
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ComposeFacts(
            api = api,
            selectedCategory = selectedCategory,
        )
    }
}

@Composable
fun ComposeFacts(
    api: NorrisApi,
    selectedCategory: String,
) {
    // As any instances inside composable function, don't forget to remember
    val factsValueManager = remember { FactsValueManager(api) }
    val facts by remember { factsValueManager.asState() }

    LaunchedEffect(selectedCategory) {
        factsValueManager.fetch(selectedCategory)
    }

    if (facts is State.Loading) {
        CircularProgressIndicator()
    }
    if (facts is State.Error) {
        Text(text = "${(facts as State.Error).exception}")
    }
    if (facts is State.Success) {
        val result = (facts as State.Success).result
        if (result.isEmpty()) {
            Text(text = "No facts found to selected category '$selectedCategory'")
        } else {
            LazyColumn {
                items(result) { item ->
                    Text(item.toString(), modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Composable
fun ComposeCategories(
    api: NorrisApi,
    onSelectCategory: (String) -> Unit,
) {
    // As any instances inside composable function, don't forget to remember
    val categoriesValueManager = remember { CategoriesValueManager(api) }

    // Categories here is a custom State class directly
    val categoriesState by remember { categoriesValueManager.categories.asState() }

    LaunchedEffect(api) {
        categoriesValueManager.fetch()
    }

    if (categoriesState is State.Loading) {
        CircularProgressIndicator()
    }
    if (categoriesState is State.Error) {
        Text(text = "${(categoriesState as State.Error).exception}")
    }
    if (categoriesState is State.Success) {
        LazyRow {
            items((categoriesState as State.Success<List<String>>).result) { item ->
                Text(item, modifier = Modifier.padding(8.dp).clickable { onSelectCategory(item) })
            }
        }
    }
}
