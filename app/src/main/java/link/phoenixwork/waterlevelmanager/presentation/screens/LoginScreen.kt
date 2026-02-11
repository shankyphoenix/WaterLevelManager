package link.phoenixwork.waterlevelmanager.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import link.phoenixwork.waterlevelmanager.UserViewModel
import link.phoenixwork.waterlevelmanager.data.ui.LoginUiState
import link.phoenixwork.waterlevelmanager.events.LoginUiEvent

@Composable
fun LoginScreen( modifier: Modifier, onClick: (id: String) -> Unit) {
    val vm: UserViewModel = hiltViewModel()
    val state by vm.uiState.collectAsState()
    val events by vm.events.collectAsState(initial = null)
    val context = LocalContext.current

    LaunchedEffect (Unit) {
        vm.events.collect { event ->
            when (event) {
                is LoginUiEvent.Toast ->
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()

                LoginUiEvent.NavigateHome ->
                    onClick("2")

                else -> {}
            }
        }
    }

    Log.d("shanky", state.toString());

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center, ){
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            OutlinedTextField(
                value = state.email,
                onValueChange = vm::onEmailChange,
                label = { Text("Username") },
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = vm::onPasswordChange,
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(12.dp))

            Button (
                onClick = vm::onSubmit,
                enabled = !state.isLoading
            ) {
                Text(if (state.isLoading) "Signing in..." else "Submit")
            }

            state.error?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = androidx.compose.ui.graphics.Color.Red) // style it red if you want
            }
            state.isLoggedIn?.let {
                Spacer(Modifier.height(8.dp))
                Text("Logged in")
            }
        }
    }

}