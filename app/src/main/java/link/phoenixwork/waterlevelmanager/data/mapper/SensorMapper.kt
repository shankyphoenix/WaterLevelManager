package link.phoenixwork.waterlevelmanager.data.mapper

import link.phoenixwork.waterlevelmanager.data.entity.WaterLevelEntity
import link.phoenixwork.waterlevelmanager.data.remote.Sensor


fun Sensor.toEntity(sensor: Sensor): WaterLevelEntity {
    return WaterLevelEntity(
        id = 1,
        distance = distance,
        percentage = percentage,
        status = status,
        serverMsg = serverMsg,
        metricFor7days = metricFor7days,
        timestampMillis = System.currentTimeMillis()
    )
}