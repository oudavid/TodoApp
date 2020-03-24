package com.okidoi.todo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.okidoi.todo.MainActivity
import com.okidoi.todo.R
import com.okidoi.todo.TaskViewModel
import com.okidoi.todo.data.TaskList
import kotlinx.android.synthetic.main.fragment_lists.*

class ListsFragment : Fragment(), ListRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: TaskViewModel by activityViewModels()

    lateinit var listAdapter: ListRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLists().observe(viewLifecycleOwner, Observer<List<TaskList>> { lists ->
            lists?.let { listAdapter.setLists(it) }
        })

        listAdapter = ListRecyclerViewAdapter(requireContext(), this)

        (list_recycler_view as RecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
            isNestedScrollingEnabled = false
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        }

        add_list_button.setOnClickListener {
            (activity as MainActivity).displayAddListFragment()
        }
    }

    override fun onDeleteClicked(list: TaskList) {
        viewModel.delete(list)
    }

    override fun onItemClicked(list: TaskList) {
        viewModel.setCurrentTaskList(list)
        (activity as MainActivity).displayTasksFragment()
    }
}