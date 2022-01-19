package com.todo.notesapp.data.repository

import androidx.lifecycle.LiveData
import com.todo.notesapp.data.ToDoNotesDao
import com.todo.notesapp.data.models.ToDoNotesData

class ToDoNotesRepository(private val toDoNotesDao: ToDoNotesDao) {

    val getAllData: LiveData<List<ToDoNotesData>> = toDoNotesDao.getAllData()

    suspend fun insertData(toDoNotesData: ToDoNotesData) {
        toDoNotesDao.insertData(toDoNotesData)
    }
}