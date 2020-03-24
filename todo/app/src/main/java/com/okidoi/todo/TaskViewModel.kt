package com.okidoi.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.okidoi.todo.data.Task
import com.okidoi.todo.data.TaskList
import com.okidoi.todo.data.TaskRepository
import com.okidoi.todo.data.TaskRoomDatabase
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository

    private var tasks: LiveData<List<Task>>? = null

    private val lists: LiveData<List<TaskList>>

    private var currentTaskList: MutableLiveData<TaskList> = MutableLiveData()

    init {
        val TasksDao = TaskRoomDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(TasksDao)
        lists = repository.lists
    }

    fun getTasks(): LiveData<List<Task>>? = tasks

    fun getLists(): LiveData<List<TaskList>> = lists

    fun getCurrentTaskList(): MutableLiveData<TaskList> = currentTaskList

    fun clearCurrentTaskList() {
        currentTaskList.value = null
    }

    fun setCurrentTaskList(taskList: TaskList) {
        currentTaskList.value = taskList
        updateTaskList(taskList.id)
    }

    fun updateTaskList(listId: Int) = viewModelScope.launch {
        tasks = repository.getTasks(listId)
    }

    fun insert(Task: Task) = viewModelScope.launch {
        repository.insertTask(Task)
    }

    fun insert(list: TaskList) = viewModelScope.launch {
        repository.insertList(list)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.updateTask(task.id, !task.isCompleted)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }

    fun delete(list: TaskList) = viewModelScope.launch {
        repository.deleteList(list)
    }

}
