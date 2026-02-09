package link.phoenixwork.waterlevelmanager.data.remote

import com.google.gson.annotations.SerializedName
import java.time.Instant

data class Sensor(
    val distance: Float = 10.0F,
    @SerializedName("created_at")
    val createdAt: String = Instant.now().toString()
)