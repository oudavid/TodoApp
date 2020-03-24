package com.okidoi.todo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.okidoi.todo.data.TaskList
import com.okidoi.todo.list.AddListFragment
import com.okidoi.todo.list.ListsFragment
import com.okidoi.todo.task.AddTaskFragment
import com.okidoi.todo.task.TasksFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels()

    private lateinit var transaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        displayListsFragment()

        taskViewModel.getCurrentTaskList().observe(this, Observer<TaskList> {
            page_title.text = it?.title ?: getString(R.string.list_page_title)
        })
    }

    fun displayTasksFragment() {
        replaceFragment(TasksFragment())
    }

    fun displayAddTaskFragment() {
        replaceFragment(AddTaskFragment())
    }

    fun displayListsFragment() {
        replaceFragment(ListsFragment())
    }

    fun displayAddListFragment() {
        replaceFragment(AddListFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
