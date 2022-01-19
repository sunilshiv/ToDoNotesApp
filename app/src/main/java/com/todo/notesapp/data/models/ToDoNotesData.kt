package com.todo.notesapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.todo.notesapp.data.models.Priority

@Entity(tableName = "todo_notes")
data class ToDoNotesData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
)
