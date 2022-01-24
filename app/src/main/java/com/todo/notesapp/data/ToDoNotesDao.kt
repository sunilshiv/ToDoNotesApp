package com.todo.notesapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.todo.notesapp.data.models.ToDoNotesData

@Dao
interface ToDoNotesDao {

    @Query("SELECT * FROM todo_notes ORDER BY id ASC")
    fun getAllData() : LiveData<List<ToDoNotesData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoNotes: ToDoNotesData)

    @Update
    suspend fun updateData(toDoNotes: ToDoNotesData)

    @Delete
    suspend fun deleteItem(toDoNotes: ToDoNotesData)

    @Query("DELETE FROM todo_notes")
    suspend fun deleteAll()

}