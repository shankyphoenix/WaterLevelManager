package link.phoenixwork.waterlevelmanager.data.entity

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.Instant

@Entity(tableName = "water_levels")
data class WaterLevelEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val distance: Float = 0f,
    val percentage: Float = 0f,
    val status: Int = 0,
    val serverMsg: String = "",
    val metricFor7days: List<Float> = emptyList(),
    val timestampMillis: Long = System.currentTimeMillis()
)


