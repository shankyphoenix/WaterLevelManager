package link.phoenixwork.waterlevelmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import link.phoenixwork.waterlevelmanager.ui.theme.WaterLevelManagerTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {

            val vm: UserViewModel = hiltViewModel()

            val sensor by vm.sensor.collectAsStateWithLifecycle()


            LaunchedEffect(Unit) {
                Log.d("shanky-OnLaunched", sensor.toString());
                vm.loadUsers() // 🔥 API call here
                Log.d("shanky-OnLaunched", sensor.toString());
            }

            LaunchedEffect(sensor) {
                Log.d("sensor", sensor.toString())
            }

            //WaterLevelScreen()

            WaterLevelManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavController(sensor, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun WaterLevelScreen(
    vm: WaterLevelViewModel = hiltViewModel()
) {
    val items by vm.levels.collectAsState()

    Column {
        Button(onClick = { vm.add(50) }) { Text("Insert level=50") }
        Button(onClick = { vm.clear() }) { Text("Clear") }

        items.forEach {
            Text("Level=${it.level}, time=${it.timestampMillis}")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WaterLevelManagerTheme {
        Greeting("Android")
    }
}