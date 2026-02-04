package link.phoenixwork.waterlevelmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import link.phoenixwork.waterlevelmanager.data.models.Sensor
import link.phoenixwork.waterlevelmanager.repo.SensorRepo
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: SensorRepo
) : ViewModel() {

    private val _users = MutableStateFlow<List<Sensor>>(emptyList())
    val users: StateFlow<List<Sensor>> = _users

    fun loadUsers() {
        viewModelScope.launch {
            _users.value = repo.fetchUsers()
        }
    }
}