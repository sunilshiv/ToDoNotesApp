package com.todo.notesapp.data.repository

import androidx.lifecycle.LiveData
import com.todo.notesapp.data.ToDoNotesDao
import com.todo.notesapp.data.models.ToDoNotesData

class ToDoNotesRepository(private val toDoNotesDao: ToDoNotesDao) {

    val getAllData: LiveData<List<ToDoNotesData>> = toDoNotesDao.getAllData()

    suspend fun insertData(toDoNotesData: ToDoNotesData) {
        toDoNotesDao.insertData(toDoNotesData)
    }

    suspend fun updateData(toDoNotesData: ToDoNotesData) {
        toDoNotesDao.updateData(toDoNotesData)
    }

    suspend fun deleteItem(toDoNotesData: ToDoNotesData) {
        toDoNotesDao.deleteItem(toDoNotesData)
    }

    suspend fun deleteAll() {
        toDoNotesDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoNotesData>> {
        return toDoNotesDao.searchDatabase(searchQuery)
    }
}