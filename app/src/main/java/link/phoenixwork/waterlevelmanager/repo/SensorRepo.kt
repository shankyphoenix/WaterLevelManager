package link.phoenixwork.waterlevelmanager.repo

import link.phoenixwork.waterlevelmanager.data.models.Sensor
import link.phoenixwork.waterlevelmanager.data.remote.MainApiInterface
import javax.inject.Inject


class SensorRepo @Inject constructor(
    private val api: MainApiInterface
) {

    suspend fun fetchUsers(): Sensor {
        return api.getDistance()
    }
}