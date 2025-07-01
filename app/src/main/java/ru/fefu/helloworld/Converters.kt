package ru.fefu.helloworld

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromActivityType(value: ActivityType): String = value.name

    @TypeConverter
    fun toActivityType(value: String): ActivityType = ActivityType.valueOf(value)

    @TypeConverter
    fun fromCoordinates(list: List<Pair<Double, Double>>): String = Gson().toJson(list)

    @TypeConverter
    fun toCoordinates(value: String): List<Pair<Double, Double>> {
        val type = object : TypeToken<List<Pair<Double, Double>>>() {}.type
        return Gson().fromJson(value, type)
    }
} 