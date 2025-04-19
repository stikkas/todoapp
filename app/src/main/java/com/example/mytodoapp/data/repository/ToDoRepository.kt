package com.example.mytodoapp.data.repository

import androidx.lifecycle.LiveData
import com.example.mytodoapp.data.ToDoDao
import com.example.mytodoapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {
    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }

    fun updateData(data: ToDoData) {
        toDoDao.updateData(data)
    }

    fun deleteData(data: ToDoData) {
        toDoDao.deleteItem(data)
    }

    fun deleteAll() {
        toDoDao.deleteAll()
    }
}