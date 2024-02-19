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
import dev.programadorthi.state.compose.extension.asState

@Composable
fun LoginScreen() {
    val viewModel = remember { LoginViewModel() }
    val usernameState = remember { viewModel.username.asState() }
    val passwordState = remember { viewModel.password.asState() }
    val (username, setUsername) = remember { usernameState }
    val (password, setPassword) = remember { passwordState }
    val validUsername by remember { usernameState.isValidAsState }
    val validPassword by remember { passwordState.isValidAsState }

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
            Text(usernameState.messages.first(), color = Color.Red)
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
            Text(passwordState.messages.first(), color = Color.Red)
        }
        Spacer(Modifier.height(20.dp))
        Button(onClick = { viewModel.login() }) {
            Text("Sign in")
        }
    }
}
