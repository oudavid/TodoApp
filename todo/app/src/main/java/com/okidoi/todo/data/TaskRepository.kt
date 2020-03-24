package com.okidoi.todo.data

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {

    val lists: LiveData<List<TaskList>> = taskDao.getLists()

    fun getTasks(listId: Int): LiveData<List<Task>> = taskDao.getTasks(listId)

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun insertList(list: TaskList) {
        taskDao.insertList(list)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun deleteList(list: TaskList) {
        taskDao.deleteList(list)
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }

    suspend fun updateTask(id: Int, isCompleted: Boolean) {
        taskDao.updateTask(id, isCompleted)
    }
}
