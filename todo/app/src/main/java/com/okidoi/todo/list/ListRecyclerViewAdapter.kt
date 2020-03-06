package com.okidoi.todo.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.okidoi.todo.R
import com.okidoi.todo.data.TaskList
import kotlinx.android.synthetic.main.list_item.view.*

class ListRecyclerViewAdapter internal constructor(
    context: Context,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ListRecyclerViewAdapter.ListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var lists = emptyList<TaskList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = inflater.inflate(R.layout.list_item, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {

        viewHolder.bind(lists[position], itemClickListener)
    }

    internal fun setLists(lists: List<TaskList>) {
        this.lists = lists
        notifyDataSetChanged()
    }

    override fun getItemCount() = lists.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listItemView: TextView = itemView.item_title
        val delete: ImageView = itemView.delete

        fun bind(list: TaskList, clickListener: OnItemClickListener) {
            listItemView.text = list.title

            delete.setOnClickListener {
                clickListener.onDeleteClicked(list)
            }

            listItemView.setOnClickListener {
                clickListener.onItemClicked(list)
            }
        }
    }

    interface OnItemClickListener {
        fun onDeleteClicked(list: TaskList)
        fun onItemClicked(list: TaskList)
    }
}





