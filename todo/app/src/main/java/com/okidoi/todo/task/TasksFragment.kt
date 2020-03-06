package com.okidoi.todo.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.okidoi.todo.MainActivity
import com.okidoi.todo.R
import com.okidoi.todo.TaskViewModel
import com.okidoi.todo.data.Task
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : Fragment(), TaskRecyclerViewAdapter.OnItemClickListener {

    private val taskViewModel: TaskViewModel by activityViewModels()

    lateinit var listAdapter: TaskRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskViewModel.getTasks()?.observe(viewLifecycleOwner, Observer<List<Task>> { tasks ->
            tasks?.let { listAdapter.setTasks(it) }
        })

        // set up the recycler view
        listAdapter = TaskRecyclerViewAdapter(requireContext(), this)

        (task_list_recycler_view as RecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
            isNestedScrollingEnabled = false
        }

        add_task_button.setOnClickListener {
            (activity as MainActivity).displayAddTaskFragment()
        }

        back.setOnClickListener {
            (activity as MainActivity).displayListsFragment()
            taskViewModel.clearCurrentTaskList()
        }
    }

    override fun onCheckBoxClicked(task: Task) {
        taskViewModel.update(task)
    }

    override fun onDeleteClicked(task: Task) {
        taskViewModel.delete(task)
    }
}