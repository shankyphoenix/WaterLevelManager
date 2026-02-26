package link.phoenixwork.waterlevelmanager

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromFloatList(list: List<Float>?): String {
        return list?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toFloatList(data: String?): List<Float> {
        return if (data.isNullOrEmpty()) {
            emptyList()
        } else {
            data.split(",").map { it.toFloat() }
        }
    }
}