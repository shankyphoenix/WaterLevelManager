package link.phoenixwork.waterlevelmanager.repo

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import link.phoenixwork.waterlevelmanager.data.entity.WaterLevelEntity
import link.phoenixwork.waterlevelmanager.domain.local.WaterLevelDao


class WaterLevelRepository @Inject constructor(
    private val dao: WaterLevelDao
) {
    fun observeAll(): Flow<List<WaterLevelEntity>> = dao.observeAll()

    suspend fun addLevel(level: Int) {
        dao.insert(WaterLevelEntity(level = level))
    }

    suspend fun getFirst():Flow<WaterLevelEntity?> = dao.getFirst()


    suspend fun clear() = dao.clear()
}