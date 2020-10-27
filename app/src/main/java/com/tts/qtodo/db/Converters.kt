package com.tts.qtodo.db

import androidx.room.TypeConverter
import com.tts.qtodo.models.Priority

class Converters {

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

}