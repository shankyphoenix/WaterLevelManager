package link.phoenixwork.waterlevelmanager.domain.remote

import link.phoenixwork.waterlevelmanager.data.remote.LoginRequest
import link.phoenixwork.waterlevelmanager.data.remote.LoginResponse
import link.phoenixwork.waterlevelmanager.data.remote.Sensor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MainApiInterface {
    @GET("get_distance")
    suspend fun getDistance(): Sensor


    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}