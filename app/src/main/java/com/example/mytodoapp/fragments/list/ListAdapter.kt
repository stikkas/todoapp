package com.example.mytodoapp.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodoapp.R
import com.example.mytodoapp.data.models.Priority
import com.example.mytodoapp.data.models.ToDoData
import com.example.mytodoapp.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var dataList = emptyList<ToDoData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        with(holder.binding) {
            val item = dataList[position]
            titleTxt.text = item.title
            descTxt.text = item.description
            val color = when (item.priority) {
                Priority.LOW -> R.color.green
                Priority.MEDIUM -> R.color.yellow
                Priority.HIGH -> R.color.red
            }
            priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    color
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(data: List<ToDoData>) {
        dataList = data
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}