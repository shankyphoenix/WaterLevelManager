package link.phoenixwork.waterlevelmanager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import link.phoenixwork.waterlevelmanager.data.ui.Login
import link.phoenixwork.waterlevelmanager.data.remote.Sensor
import link.phoenixwork.waterlevelmanager.events.LoginUiEvent

import link.phoenixwork.waterlevelmanager.repo.SensorRepo
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: SensorRepo
) : ViewModel() {

    private val _sensor = MutableStateFlow<Sensor>(Sensor())
    private val _login = MutableStateFlow<Login>(Login())
    val sensor: StateFlow<Sensor?> = _sensor

    init {
       // startAutoRefresh()
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            while (isActive) {
                loadUsers()
                delay(3000) // ⏱️ 10 seconds
            }
        }
    }


    fun loadUsers() {
        viewModelScope.launch {
            Log.d("Shanky", _sensor.value.toString())
            _sensor.value = repo.fetchUsers() // ✅ Sensor → Sensor?
            Log.d("Shanky", _sensor.value.toString())
        }
    }

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> {
                // 🔹 update() = convenient way to change StateFlow state immutably
                _login.update { it.copy(email = event.value, error = "") }
            }

            is LoginUiEvent.PasswordChanged -> {
                _login.update { it.copy(password = event.value, error = "") }
            }

           // LoginUiEvent.LoginClicked -> login()
            else -> {}
        }
    }
}