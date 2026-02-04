package link.phoenixwork.waterlevelmanager.data.remote


import link.phoenixwork.waterlevelmanager.data.ui.Sensor
import retrofit2.http.GET

interface MainApiInterface {
    @GET("get_distance")
    suspend fun getDistance(): Sensor
}