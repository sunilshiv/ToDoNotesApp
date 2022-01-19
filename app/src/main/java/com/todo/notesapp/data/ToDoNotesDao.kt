package com.todo.notesapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.todo.notesapp.data.models.ToDoNotesData

@Dao
interface ToDoNotesDao {

    @Query("SELECT * FROM todo_notes ORDER BY id ASC")
    fun getAllData() : LiveData<List<ToDoNotesData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoNotes: ToDoNotesData)

}