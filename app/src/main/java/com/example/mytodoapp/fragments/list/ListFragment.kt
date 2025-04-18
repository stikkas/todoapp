package com.example.mytodoapp.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodoapp.R
import com.example.mytodoapp.data.viewmodel.ToDoViewModel
import com.example.mytodoapp.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private val vm: ToDoViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentListBinding.inflate(inflater, container, false)
        with(binding) {
            addBtn.setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }
            listLayout.setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_updateFragment)
            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        }
        vm.getAllData.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }
}