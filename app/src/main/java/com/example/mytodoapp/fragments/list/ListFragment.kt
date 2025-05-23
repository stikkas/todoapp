package com.example.mytodoapp.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mytodoapp.R
import com.example.mytodoapp.data.models.ToDoData
import com.example.mytodoapp.data.viewmodel.ToDoViewModel
import com.example.mytodoapp.databinding.FragmentListBinding
import com.example.mytodoapp.fragments.SharedViewModel
import com.example.mytodoapp.fragments.list.adapter.ListAdapter
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val vm: ToDoViewModel by viewModels()
    private val sharedViewModel by viewModels<SharedViewModel>()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentListBinding.inflate(inflater, container, false)
        with(binding) {
            recyclerView.adapter = adapter
//            recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
//            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            recyclerView.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
            this.lifecycleOwner = this@ListFragment
            this.svm = sharedViewModel
            swipeToDelete(recyclerView)
        }
        vm.getAllData.observe(viewLifecycleOwner, Observer {
            sharedViewModel.checkIfDatabaseEmpty(it)
            adapter.setData(it)
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        (menu.findItem(R.id.menuSearch).actionView as? SearchView)?.let {
            it.isSubmitButtonEnabled = true
            it.setOnQueryTextListener(this)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuDeleteAll) {
            deleteAll()
        } else {
            when (item.itemId) {
                R.id.mHiPriority -> vm.sortByHighPriority
                R.id.mLoPriority -> vm.sortByLowPriority
                else -> null
            }?.observe(this, Observer { adapter.setData(it) })
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { search(it) }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        query?.let { search(it) }
        return true
    }

    private fun search(query: String) {
        vm.search("%$query%").observe(this, Observer {
            adapter.setData(it)
        })
    }

    private fun swipeToDelete(view: RecyclerView) {
        val callback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = adapter.dataList[position]
                vm.deleteData(item)
                adapter.notifyItemRemoved(position)
                restoreDeletedData(viewHolder.itemView, item, position)
            }
        }
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(view)
    }

    private fun restoreDeletedData(view: View, delItem: ToDoData, position: Int) {
        val snackBar = Snackbar.make(
            view, "Deleted '${delItem.title}", Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            vm.insertData(delItem)
            adapter.notifyItemChanged(position)
        }
        snackBar.show()
    }

    private fun deleteAll() {
        AlertDialog.Builder(requireContext()).apply {
            setPositiveButton("YES") { _, _ ->
                vm.deleteAll()
                Toast.makeText(
                    requireContext(), "Successfully Removed All Data",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setNegativeButton("NO") { _, _ -> }
            setTitle("Delete all data?")
            setMessage("Are you sure you want to remove all items?")
        }.create().show()
    }

}