package com.example.mytodoapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mytodoapp.data.models.ToDoData

@Dao
interface ToDoDao {
    @Query("select * from todo order by id asc")
    fun getAllData(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertData(data: ToDoData)

    @Update
    fun updateData(data: ToDoData)

    @Delete
    fun deleteItem(data: ToDoData)

    @Query("delete from todo")
    fun deleteAll()
}