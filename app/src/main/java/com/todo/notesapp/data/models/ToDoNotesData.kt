package com.todo.notesapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.todo.notesapp.data.models.Priority
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "todo_notes")
@Parcelize
data class ToDoNotesData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
): Parcelable
