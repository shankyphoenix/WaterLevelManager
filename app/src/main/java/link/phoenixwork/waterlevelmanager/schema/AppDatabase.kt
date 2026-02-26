package link.phoenixwork.waterlevelmanager.schema

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import link.phoenixwork.waterlevelmanager.Converters
import link.phoenixwork.waterlevelmanager.data.entity.WaterLevelEntity
import link.phoenixwork.waterlevelmanager.domain.local.WaterLevelDao

@Database(
    entities = [WaterLevelEntity::class],
    version = 1,
    exportSchema = true
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun waterLevelDao(): WaterLevelDao
}
