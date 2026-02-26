package link.phoenixwork.waterlevelmanager

import retrofit2.HttpException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import link.phoenixwork.waterlevelmanager.data.entity.WaterLevelEntity
import link.phoenixwork.waterlevelmanager.data.remote.Sensor
import link.phoenixwork.waterlevelmanager.repo.SensorRepo
import link.phoenixwork.waterlevelmanager.repo.WaterLevelRepository
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

sealed class SensorStatus {
    object Idle : SensorStatus()
    object Loading : SensorStatus()
    data class Success(val sensor: Sensor) : SensorStatus()
    data class NoInternet(val msg: String = "No internet") : SensorStatus()
    data class Timeout(val msg: String = "Timeout") : SensorStatus()
    data class Unreachable(val msg: String = "Server unreachable") : SensorStatus()
    data class TokenExpired(val msg: String = "Token expired") : SensorStatus()
    data class HttpError(val code: Int, val msg: String) : SensorStatus()
    data class Unknown(val msg: String) : SensorStatus()
}

@HiltViewModel
class WaterLevelViewModel @Inject constructor(
    private val repo: WaterLevelRepository,
    private val SenserRepo: SensorRepo
) : ViewModel() {

    val sensorData: StateFlow<WaterLevelEntity?> =
        repo.getFirst()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    private val _sensorStatus = MutableStateFlow<SensorStatus>(SensorStatus.Idle)
    val sensorStatus = _sensorStatus.asStateFlow()

    private var pollingJob: Job? = null

    // ✅ call when screen opens
    fun startPolling(periodMs: Long = 30_000L) {
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            while (isActive) {
                _sensorStatus.value = SensorStatus.Loading

                val status = fetchSensorSafely()
                _sensorStatus.value = status

                if (status is SensorStatus.Success) {
                    val distance = status.sensor.distance ?: 0f

                    Log.d("shanky-fromapi", status.sensor.toString())

                    repo.upsertLevel(status.sensor) // ✅ update Room first
                    Log.d("shanky", "Saved distance=$distance")
                } else {
                    Log.e("shanky", "Fetch failed: $status")
                }

                delay(periodMs)
            }
        }
    }

    // ✅ call when screen closes
    fun stopPolling() {
        pollingJob?.cancel()
        pollingJob = null
        _sensorStatus.value = SensorStatus.Idle
    }

    private suspend fun fetchSensorSafely(): SensorStatus {
        return try {
            val sensor = SenserRepo.fetchUsers()
            SensorStatus.Success(sensor)
        } catch (e: UnknownHostException) {
            // no internet / DNS fail
            SensorStatus.NoInternet(e.message ?: "No internet / DNS error")
        } catch (e: SocketTimeoutException) {
            // timeout
            SensorStatus.Timeout(e.message ?: "Timeout")
        } catch (e: HttpException) {
            // HTTP errors like 401/403/500
            when (e.code()) {
                401 -> SensorStatus.TokenExpired("401 Unauthorized (token expired)")
                403 -> SensorStatus.HttpError(403, "403 Forbidden")
                404 -> SensorStatus.HttpError(404, "404 Not Found")
                500, 502, 503, 504 -> SensorStatus.Unreachable("${e.code()} Server error")
                else -> SensorStatus.HttpError(e.code(), e.message())
            }
        } catch (e: IOException) {
            // connection reset/refused, unreachable host, etc.
            SensorStatus.Unreachable(e.message ?: "Network I/O error")
        } catch (e: Exception) {
            SensorStatus.Unknown(e.message ?: "Unknown error")
        }
    }

/*
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
    }*/


    fun clear() = viewModelScope.launch {
        repo.clear()
    }
}