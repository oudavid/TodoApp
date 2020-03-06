package com.okidoi.todo.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    // with live data we get notified with the database has changed
    @Query("SELECT * FROM task_table WHERE list_id = :listId")
    fun getTasks(listId: Int): LiveData<List<Task>>

    @Query("SELECT * FROM list_table")
    fun getLists(): LiveData<List<TaskList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: TaskList)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()

    @Delete
    suspend fun deleteTask(task: Task)

    @Delete
    suspend fun deleteList(list: TaskList)

    @Query("UPDATE task_table SET is_completed = :isCompleted WHERE id = :id")
    suspend fun updateTask(id: Int, isCompleted: Boolean)
}
