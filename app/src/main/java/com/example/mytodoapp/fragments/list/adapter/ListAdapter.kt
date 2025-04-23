package com.example.mytodoapp.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodoapp.data.models.ToDoData
import com.example.mytodoapp.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    var dataList = emptyList<ToDoData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder( holder: MyViewHolder, position: Int ) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(data: List<ToDoData>) {
        val util = ToDoDiffUtil(dataList, data)
        val result = DiffUtil.calculateDiff(util)
        dataList = data
        result.dispatchUpdatesTo(this)
    }

    class MyViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ToDoData) {
            binding.toDoData = data
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val binding = RowLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return MyViewHolder(binding)
            }
        }
    }
}