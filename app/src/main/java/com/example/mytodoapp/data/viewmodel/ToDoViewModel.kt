package com.example.mytodoapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.data.ToDoDb
import com.example.mytodoapp.data.models.ToDoData
import com.example.mytodoapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(app: Application) : AndroidViewModel(app) {
    private val toDoDao = ToDoDb.getDatabase(app).toDoDao()
    private val repository: ToDoRepository = ToDoRepository(toDoDao)

    val getAllData: LiveData<List<ToDoData>> = repository.getAllData
    val sortByHighPriority = repository.sortByHighPriority
    val sortByLowPriority = repository.sortByLowPriority

    fun insertData(data: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(data)
        }
    }

    fun updateData(data: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(data)
        }
    }

    fun deleteData(data: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(data)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun search(query: String): LiveData<List<ToDoData>> {
        return repository.search(query)
    }
}