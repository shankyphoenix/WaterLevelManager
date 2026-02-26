package link.phoenixwork.waterlevelmanager.repo

import android.util.Log
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import link.phoenixwork.waterlevelmanager.data.entity.WaterLevelEntity
import link.phoenixwork.waterlevelmanager.data.mapper.toEntity
import link.phoenixwork.waterlevelmanager.data.remote.Sensor
import link.phoenixwork.waterlevelmanager.domain.local.WaterLevelDao


class WaterLevelRepository @Inject constructor(
    private val dao: WaterLevelDao
) {
    fun observeAll(): Flow<List<WaterLevelEntity>> = dao.observeAll()

    fun getFirst(): Flow<WaterLevelEntity?> = dao.getFirst()

    suspend fun addLevel(level: Float) {
        //dao.insert(WaterLevelEntity(level = level))
    }
    suspend fun upsertLevel(sensor: Sensor) {
        //dao.upsert(level, 0f, 0f, 0, "", emptyList(), System.currentTimeMillis())
        val waterLevelData = sensor.toEntity(sensor)
        dao.upsertWaterLevel(waterLevelData)
    }

    suspend fun clear() = dao.clear()
}