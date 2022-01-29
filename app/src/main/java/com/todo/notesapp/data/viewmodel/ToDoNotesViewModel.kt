package com.todo.notesapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.todo.notesapp.data.ToDoNotesDatabase
import com.todo.notesapp.data.models.ToDoNotesData
import com.todo.notesapp.data.repository.ToDoNotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoNotesViewModel(application: Application): AndroidViewModel(application) {

    private val toDoNotesDao = ToDoNotesDatabase.getDatabase(application).toDoNotesDao()
    private val toDoNotesRepository : ToDoNotesRepository
    val getAllData: LiveData<List<ToDoNotesData>>
    val sortyByHighPriority: LiveData<List<ToDoNotesData>>
    val sortyByLowPriority: LiveData<List<ToDoNotesData>>

    init {
        toDoNotesRepository = ToDoNotesRepository(toDoNotesDao)
        getAllData = toDoNotesRepository.getAllData
        sortyByHighPriority = toDoNotesRepository.sortByHighPriority
        sortyByLowPriority = toDoNotesRepository.sortByLowPriority
    }

    fun insertData(toDoNotesData: ToDoNotesData) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoNotesRepository.insertData(toDoNotesData)
        }
    }

    fun updateData(toDoNotesData: ToDoNotesData) {
        viewModelScope.launch (Dispatchers.IO){
            toDoNotesRepository.updateData(toDoNotesData)
        }
    }

    fun deleteItem(toDoNotesData: ToDoNotesData) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoNotesRepository.deleteItem(toDoNotesData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoNotesRepository.deleteAll()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoNotesData>> {
        return toDoNotesRepository.searchDatabase(searchQuery)
    }
}