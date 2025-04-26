package com.example.mytodoapp.data.repository

import androidx.lifecycle.LiveData
import com.example.mytodoapp.data.ToDoDao
import com.example.mytodoapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {
    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(data: ToDoData) {
        toDoDao.updateData(data)
    }

    suspend fun deleteData(data: ToDoData) {
        toDoDao.deleteItem(data)
    }

    suspend fun deleteAll() {
        toDoDao.deleteAll()
    }

    fun search(query: String): LiveData<List<ToDoData>> {
        return toDoDao.search(query)
    }
}