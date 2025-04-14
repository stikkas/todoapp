package com.example.mytodoapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mytodoapp.data.models.ToDoData

@Dao
interface ToDoDao {
    @Query("select * from todo order by id asc")
    fun getAllData(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(data: ToDoData)
}