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

    @Query("SELECT * FROM todo_notes WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<ToDoNotesData>>

    @Query("SELECT * FROM todo_notes ORDER BY CASE WHEN priority LIKE 'H%'THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END ")
    fun sortByHighPriority(): LiveData<List<ToDoNotesData>>

    @Query("SELECT * FROM todo_notes ORDER BY CASE WHEN priority LIKE 'L%'THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END ")
    fun sortByLowPriority(): LiveData<List<ToDoNotesData>>

}