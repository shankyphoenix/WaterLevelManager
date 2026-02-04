package link.phoenixwork.waterlevelmanager.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text

@Composable
fun WaterLevelDisplay(name: String, modifier: Modifier, onClick: (id: String) -> Unit) {

    Box(modifier= modifier) {
        Text(text = "Water Level $name!",modifier=modifier)

        OutlinedButton(onClick = { onClick("2") }) {
            Text(name)
        }
    }

}