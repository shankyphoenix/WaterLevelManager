package link.phoenixwork.waterlevelmanager.domain.remote

import link.phoenixwork.waterlevelmanager.data.remote.Sensor
import retrofit2.http.GET

interface MainApiInterface {
    @GET("get_distance")
    suspend fun getDistance(): Sensor
}