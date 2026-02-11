package link.phoenixwork.waterlevelmanager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import link.phoenixwork.waterlevelmanager.data.remote.Sensor
import link.phoenixwork.waterlevelmanager.data.ui.LoginUiState
import link.phoenixwork.waterlevelmanager.events.LoginUiEvent
import link.phoenixwork.waterlevelmanager.repo.AuthRepo

import link.phoenixwork.waterlevelmanager.repo.SensorRepo
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: SensorRepo,
    private val authRepo: AuthRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _events = MutableSharedFlow<LoginUiEvent>()
    val events = _events.asSharedFlow()

    fun onEmailChange(v: String) {
        _uiState.update { it.copy(email = v, error = null) }
    }

    fun onPasswordChange(v: String) {
        _uiState.update { it.copy(password = v, error = null) }
    }

    fun onSubmit() = viewModelScope.launch {
        val s = uiState.value
        if (s.email.isBlank() || s.password.isBlank()) {
            _uiState.update { it.copy(error = "Username and password required") }
            _events.emit(LoginUiEvent.Toast("Please fill all fields"))
            return@launch
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        authRepo.login(s.email, s.password)
            .onSuccess { response ->
                // optionally: save token from response.token
                Log.d("shanky",response.toString())
                if(response.success){
                    _uiState.update { it.copy(isLoggedIn = true) }
                    _events.emit(LoginUiEvent.NavigateHome)
                } else {
                    _uiState.update { it.copy(error = response.message ?: "Something went wrong") }
                    _events.emit(LoginUiEvent.Toast("Login failed"))
                }
            }
            .onFailure { e ->
                _uiState.update { it.copy(error = e.message ?: "Something went wrong") }
                _events.emit(LoginUiEvent.Toast("Login failed"))
            }

        _uiState.update { it.copy(isLoading = false) }
    }
}