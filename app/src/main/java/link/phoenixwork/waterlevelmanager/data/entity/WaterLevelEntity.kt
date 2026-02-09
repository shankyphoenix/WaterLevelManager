package link.phoenixwork.waterlevelmanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_levels")
data class WaterLevelEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val level: Int,
    val timestampMillis: Long = System.currentTimeMillis()
)
