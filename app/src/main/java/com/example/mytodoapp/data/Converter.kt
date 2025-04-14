package com.example.mytodoapp.data

import androidx.room.TypeConverter
import com.example.mytodoapp.data.models.Priority

class Converter {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(name: String): Priority {
       return Priority.valueOf(name)
    }
}