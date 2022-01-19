package com.todo.notesapp.data

import androidx.room.TypeConverter
import com.todo.notesapp.data.models.Priority

class PriorityConverter {
    @TypeConverter
    fun fromPriority(priority: Priority) : String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}