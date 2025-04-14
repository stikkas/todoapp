package com.example.mytodoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
)
