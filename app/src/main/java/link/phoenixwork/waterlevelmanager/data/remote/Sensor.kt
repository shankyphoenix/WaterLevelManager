package link.phoenixwork.waterlevelmanager.data.remote

import com.google.gson.annotations.SerializedName
import java.time.Instant

data class Sensor(
    @SerializedName("distance")
    val distance: Float = 0.0F,

    @SerializedName("percentage")
    val percentage: Float = 0.0F,

    @SerializedName("status")
    val status: Int = 0,

    @SerializedName("server_msg")
    val serverMsg: String = "",

    @SerializedName("metrics_of_7_days")
    val metricFor7days:List<Float> = emptyList(),

    @SerializedName("created_at")
    val createdAt: String? = null)