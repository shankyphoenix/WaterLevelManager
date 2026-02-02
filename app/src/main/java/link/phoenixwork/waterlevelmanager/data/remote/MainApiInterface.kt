package link.phoenixwork.waterlevelmanager.data.remote

import link.phoenixwork.waterlevelmanager.data.models.Sensor
import retrofit2.http.GET

interface MainApiInterface {
    @GET("get_distance")
    suspend fun getUsers(): List<Sensor>
}