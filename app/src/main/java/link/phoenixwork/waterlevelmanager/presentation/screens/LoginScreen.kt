package link.phoenixwork.waterlevelmanager.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text

@Composable
fun LoginScreen(name: String, modifier: Modifier, onClick: (id: String) -> Unit) {

    Column (modifier= modifier) {

        OutlinedButton(onClick = { onClick("2") }) {
            Text(name)
        }
        OutlinedButton(onClick = { onClick("2") }) {
            Text("Shanky")
        }
        Text(text = "Hello $name!",modifier=modifier)
    }

}