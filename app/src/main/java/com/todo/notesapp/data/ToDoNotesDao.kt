package com.todo.notesapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ToDoNotesDao {

    @Query("SELECT * FROM todo_notes ORDER BY id ASC")
    fun getAll() : LiveData<ToDoNotesData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoNotes: ToDoNotesData)

}