package com.example.mytodoapp.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mytodoapp.R
import com.example.mytodoapp.data.models.ToDoData
import com.example.mytodoapp.data.viewmodel.ToDoViewModel
import com.example.mytodoapp.databinding.FragmentAddBinding
import com.example.mytodoapp.fragments.SharedViewModel


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val viewModel by viewModels<ToDoViewModel>()
    private val sharedVm by viewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentAddBinding.inflate(inflater, container, false)
        binding.spinner.onItemSelectedListener = sharedVm.listener
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuAdd) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        with(binding) {
            val title = titleEd.text.toString()
            val priority = spinner.selectedItem.toString()
            val desc = descEd.text.toString()

            if (sharedVm.verifyData(title, desc)) {
                val data = ToDoData(0, title, sharedVm.parsePriority(priority), desc)
                viewModel.insertData(data)
                Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}