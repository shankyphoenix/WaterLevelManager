package link.phoenixwork.waterlevelmanager.data.models
import com.google.gson.annotations.SerializedName
import java.time.Instant

data class Sensor(
    val distance: String?,
    @SerializedName("created_at")
    val createdAt: String = Instant.now().toString()
)
