package link.phoenixwork.waterlevelmanager

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import link.phoenixwork.waterlevelmanager.data.remote.Sensor
import link.phoenixwork.waterlevelmanager.presentation.screens.LoginScreen
import link.phoenixwork.waterlevelmanager.presentation.screens.WaterLevelDisplay

@Composable
fun AppNavController(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "test") {
        composable("test") {
            LoginScreen( modifier) { id ->
                navController.navigate("test2")
            }
        }
        composable("test2") {
            WaterLevelDisplay (modifier){ id ->
                navController.navigate("test")
            }
        }
    }
}