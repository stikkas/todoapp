package com.example.mytodoapp.fragments

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mytodoapp.R
import com.example.mytodoapp.data.models.Priority
import com.example.mytodoapp.data.models.ToDoData

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(data: List<ToDoData>) {
        emptyDatabase.value = data.isEmpty()
    }

    val listener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            val color = when (pos) {
                0 -> R.color.red
                1 -> R.color.yellow
                2 -> R.color.green
                else -> R.color.black
            }
            (parent?.getChildAt(0) as? TextView)?.setTextColor(
                ContextCompat.getColor(application, color)
            )
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }

    fun verifyData(title: String, description: String): Boolean {
        return title.isNotBlank() && description.isNotBlank()
    }

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> Priority.HIGH
            "Medium Priority" -> Priority.MEDIUM
            else -> Priority.LOW
        }
    }
}