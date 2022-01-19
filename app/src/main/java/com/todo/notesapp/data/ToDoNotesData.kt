package com.todo.notesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_notes")
data class ToDoNotesData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
)
