package link.phoenixwork.waterlevelmanager.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import link.phoenixwork.waterlevelmanager.data.entity.WaterLevelEntity


@Dao
interface WaterLevelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: WaterLevelEntity): Long


    @Query("SELECT * FROM water_levels ORDER BY timestampMillis DESC")
    fun observeAll(): Flow<List<WaterLevelEntity>>


   /* @Query("""
        INSERT OR REPLACE INTO water_levels (id, level, distance, percentage, status, serverMsg, metricFor7days, timestampMillis)
        VALUES(1,:level,:distance,:percentage,:status,:serverMsg,:metricFor7days,:timestamp)
    """)suspend fun upsert(level: Float, distance: Float, percentage: Float, status: Int, serverMsg: String, metricFor7days: List<Float>, timestamp: Long)*/
   @Upsert
    suspend fun upsertWaterLevel(waterLevel: WaterLevelEntity)



    @Query("SELECT * FROM water_levels where id = 1")
    fun getFirst(): Flow<WaterLevelEntity?>

    @Query("DELETE FROM water_levels")
    suspend fun clear()
}