package dev.programadorthi.common.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.programadorthi.state.core.extension.messages

@Composable
fun LoginScreen() {
    val viewModel = remember { LoginViewModel() }
    val (username, setUsername) = remember { viewModel.username }
    val (password, setPassword) = remember { viewModel.password }
    val validUsername by remember { viewModel.username.isValid }
    val validPassword by remember { viewModel.password.isValid }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Sign in to Start")
        Spacer(Modifier.height(20.dp))
        TextField(
            isError = validUsername.not(),
            label = {
                Text("Username: ")
            },
            value = username,
            onValueChange = setUsername,
        )
        if (validUsername.not()) {
            Text(viewModel.username.messages().first(), color = Color.Red)
        }
        Spacer(Modifier.height(20.dp))
        TextField(
            isError = validPassword.not(),
            label = {
                Text("Password: ")
            },
            value = password,
            onValueChange = setPassword,
        )
        if (validPassword.not()) {
            Text(viewModel.password.messages().first(), color = Color.Red)
        }
        Spacer(Modifier.height(20.dp))
        Button(onClick = { viewModel.login() }) {
            Text("Sign in")
        }
    }
}
