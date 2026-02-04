package link.phoenixwork.waterlevelmanager

import android.util.Log
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

    private val _sensor = MutableStateFlow<Sensor?>(null)
    val sensor: StateFlow<Sensor?> = _sensor

    fun loadUsers() {
        viewModelScope.launch {
            _sensor.value = repo.fetchUsers() // ✅ Sensor → Sensor?
            Log.d("Shanky", _sensor.value.toString())
        }
    }
}