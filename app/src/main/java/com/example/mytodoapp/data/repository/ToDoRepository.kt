package com.example.mytodoapp.data.repository

import androidx.lifecycle.LiveData
import com.example.mytodoapp.data.ToDoDao
import com.example.mytodoapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {
    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }
}