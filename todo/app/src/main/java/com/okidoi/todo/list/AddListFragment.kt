package com.okidoi.todo.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.okidoi.todo.MainActivity
import com.okidoi.todo.R
import com.okidoi.todo.TaskViewModel
import com.okidoi.todo.data.TaskList
import kotlinx.android.synthetic.main.fragment_add_list.*

class AddListFragment : Fragment() {

    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancel.setOnClickListener {
            (activity as MainActivity).displayListsFragment()
        }

        button_save.setOnClickListener {
            if (edit_title.text.toString().isNotEmpty()) {
                val list = TaskList(
                    title = edit_title.text.toString()
                )
                taskViewModel.insert(list)
                (activity as MainActivity).displayListsFragment()
            } else {
                val errorMsg = "Failed to save task list!"
                Log.e("AddListFragment", errorMsg)
                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
            }
        }

    }

}