package com.example.mytodoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mytodoapp.data.models.ToDoData

@Database(entities = [ToDoData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDb : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoDb? = null

        fun getDatabase(context: Context): ToDoDb = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                ToDoDb::class.java,
                "todo_database"
            ).build().also {
                INSTANCE = it
            }
        }
    }
}