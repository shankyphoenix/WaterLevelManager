package link.phoenixwork.waterlevelmanager.repo


import link.phoenixwork.waterlevelmanager.domain.remote.MainApiInterface
import link.phoenixwork.waterlevelmanager.data.remote.Sensor
import javax.inject.Inject


class SensorRepo @Inject constructor(
    private val api: MainApiInterface
) {

    suspend fun fetchUsers(): Sensor {
        return api.getDistance()
    }
}