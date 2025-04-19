package com.example.mytodoapp.fragments.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodoapp.R
import com.example.mytodoapp.data.models.Priority
import com.example.mytodoapp.data.models.ToDoData
import com.example.mytodoapp.data.viewmodel.ToDoViewModel
import com.example.mytodoapp.databinding.FragmentAddBinding
import com.example.mytodoapp.databinding.FragmentUpdateBinding
import com.example.mytodoapp.fragments.SharedViewModel

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private val viewModel by viewModels<ToDoViewModel>()
    private val sharedViewModel by viewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        with(binding) {
            val item = args.currentItem
            curTitleEd.setText(item.title)
            curDescEd.setText(item.description)
            val pos = when (item.priority) {
                Priority.HIGH -> 0
                Priority.MEDIUM -> 1
                else -> 2
            }
            curSpinner.onItemSelectedListener = sharedViewModel.listener
            curSpinner.setSelection(pos)

        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuSave -> updateData()
            R.id.menuDelete -> removeItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun removeItem() {
        AlertDialog.Builder(requireContext()).apply {
            setPositiveButton("YES") { _, _ ->
                viewModel.deleteData(args.currentItem)
                Toast.makeText(
                    requireContext(), "Successfully Removed: ${args.currentItem.title}",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            setNegativeButton("NO") { _, _ -> }
            setTitle("Delete \"${args.currentItem.title}?\"")
            setMessage("Are you sure you want to remove \"${args.currentItem.title}?\"")
        }.create().show()
    }

    private fun updateData() {
        with(binding) {
            val title = curTitleEd.text.toString()
            val desc = curDescEd.text.toString()
            val priority = when (curSpinner.selectedItemPosition) {
                0 -> Priority.HIGH
                1 -> Priority.MEDIUM
                else -> Priority.LOW
            }
            if (sharedViewModel.verifyData(title, desc)) {
                val item = ToDoData(args.currentItem.id, title, priority, desc)
                viewModel.updateData(item)
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            } else {
                Toast.makeText(requireContext(), "Please, fill data out", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
