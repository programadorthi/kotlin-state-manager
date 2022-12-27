package dev.programadorthi.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.programadorthi.common.api.NorrisApi
import dev.programadorthi.common.state.CategoriesValueManager
import dev.programadorthi.common.state.FactsValueManager
import dev.programadorthi.common.state.State
import dev.programadorthi.compose.extension.rememberComposeValueManager
import dev.programadorthi.core.extension.getValue
import dev.programadorthi.core.extension.setValue
import kotlinx.coroutines.Dispatchers

@Composable
fun App() {
    val api = remember { NorrisApi() }

    // You can create your composed value directly
    // Using = instead of 'by' you can access flow or value manager functions
    var selectedCategory by rememberComposeValueManager(initialValue = "random")

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
    val factsValueManager = remember { FactsValueManager(api, Dispatchers.Main) }

    // Facts here is Compose State consumed by delegate properties
    val facts = factsValueManager.value

    LaunchedEffect(selectedCategory) {
        factsValueManager.fetch(selectedCategory)
    }

    if (facts is State.Loading) {
        CircularProgressIndicator()
    }
    if (facts is State.Error) {
        Text(text = "${facts.exception}")
    }
    if (facts is State.Success) {
        if (facts.result.isEmpty()) {
            Text(text = "No facts found to selected category '$selectedCategory'")
        } else {
            LazyColumn {
                items(facts.result) { item ->
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
    val categoriesState = categoriesValueManager.categories

    LaunchedEffect(api) {
        categoriesValueManager.fetch()
    }

    if (categoriesState is State.Loading) {
        CircularProgressIndicator()
    }
    if (categoriesState is State.Error) {
        Text(text = "${categoriesState.exception}")
    }
    if (categoriesState is State.Success) {
        LazyRow {
            items(categoriesState.result) { item ->
                Text(item, modifier = Modifier.padding(8.dp).clickable { onSelectCategory(item) })
            }
        }
    }
}
