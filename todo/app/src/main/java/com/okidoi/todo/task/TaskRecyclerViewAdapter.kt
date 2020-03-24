package com.okidoi.todo.task

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.okidoi.todo.R
import com.okidoi.todo.data.Task
import kotlinx.android.synthetic.main.task_item.view.*

class TaskRecyclerViewAdapter internal constructor(
    private val context: Context,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tasks = emptyList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = inflater.inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: TaskViewHolder, position: Int) {

        viewHolder.bind(tasks[position], itemClickListener)
    }

    internal fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun getItemCount() = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskItemView: TextView = itemView.item_title
        val checkBox: ImageView = itemView.checkbox
        val delete: ImageView = itemView.delete

        fun bind(task: Task, clickListener: OnItemClickListener) {
            taskItemView.text = task.title

            if (task.isCompleted) {
                taskItemView.setTextColor(getColor(context, R.color.colorGray))
                checkBox.setImageDrawable(
                    getDrawable(
                        context,
                        R.drawable.ic_check_box_completed_24px
                    )
                )
            } else {
                taskItemView.setTextColor(getColor(context,
                    R.color.colorPrimaryDark
                ))
                checkBox.setImageDrawable(
                    getDrawable(
                        context,
                        R.drawable.ic_check_box_incomplete_24px
                    )
                )
            }

            checkBox.setOnClickListener {
                clickListener.onCheckBoxClicked(task)
            }
            delete.setOnClickListener {
                clickListener.onDeleteClicked(task)
            }
        }
    }

    interface OnItemClickListener {
        fun onCheckBoxClicked(task: Task)
        fun onDeleteClicked(task: Task)
    }
}





