package ru.fefu.helloworld

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: ActivityType,
    val startTime: Long,
    val finishTime: Long,
    val coordinates: String // JSON-строка или сериализованный список пар (lat, lon)
)

enum class ActivityType(val iconRes: Int) {
    BIKE(R.drawable.baseline_play_arrow_24),
    RUN(R.drawable.baseline_play_arrow_24),
    WALK(R.drawable.baseline_play_arrow_24)
} 