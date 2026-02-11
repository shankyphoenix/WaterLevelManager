package link.phoenixwork.waterlevelmanager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import link.phoenixwork.waterlevelmanager.data.entity.WaterLevelEntity
import link.phoenixwork.waterlevelmanager.data.remote.Sensor
import link.phoenixwork.waterlevelmanager.repo.SensorRepo
import link.phoenixwork.waterlevelmanager.repo.WaterLevelRepository
import javax.inject.Inject


@HiltViewModel
class WaterLevelViewModel @Inject constructor(
    private val repo: WaterLevelRepository,
    private val SenserRepo: SensorRepo
) : ViewModel() {

    val levels: StateFlow<WaterLevelEntity?> =
        repo.getFirst()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    private val sensorPollingFlow = kotlinx.coroutines.flow.flow {
        while (true) {
            emit(SenserRepo.fetchUsers())
            delay(5_000)
        }
    }

    init {
        viewModelScope.launch {
            sensorPollingFlow.collect { sensor ->
                repo.upsertLevel(sensor.distance ?: 0F)
                Log.d("shanky", sensor.toString())
            }
        }
    }


    fun add(level: Float) = viewModelScope.launch {
        //repo.addLevel(level)
        repo.upsertLevel(level)
        val a: Sensor = SenserRepo.fetchUsers();

        Log.d("shanky",a.toString())

        Log.d("shanky","added")
    }

    fun clear() = viewModelScope.launch {
        repo.clear()
    }
}