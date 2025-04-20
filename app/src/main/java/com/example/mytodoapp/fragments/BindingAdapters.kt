package com.example.mytodoapp.fragments

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.example.mytodoapp.R
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {
    companion object {
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, empty: MutableLiveData<Boolean>) {
            view.visibility = if (empty.value == true) View.VISIBLE else View.INVISIBLE
        }
    }
}