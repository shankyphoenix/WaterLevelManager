package link.phoenixwork.waterlevelmanager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import link.phoenixwork.waterlevelmanager.data.remote.Sensor
import link.phoenixwork.waterlevelmanager.presentation.screens.LoginScreen
import link.phoenixwork.waterlevelmanager.presentation.screens.WaterLevelDisplay

@Composable
fun AppNavController(modifier: Modifier) {
    val context = LocalContext.current
    val navController = rememberNavController()

    val tokenStorage = remember { TokenStorage(context) }
    val token = remember { tokenStorage.getToken() }

    val start = if (!token.isNullOrBlank()) "test2" else "test"


    NavHost(navController = navController, startDestination = start) {



        composable("test") {
            LoginScreen( modifier) { id ->
                navController.navigate("test2") {
                    popUpTo("test") { inclusive = true }  // remove login from backstack
                    launchSingleTop = true
                }
            }
        }
        composable("test2") {
            WaterLevelDisplay (modifier){ id ->
                navController.navigate("test")
            }
        }
    }
}