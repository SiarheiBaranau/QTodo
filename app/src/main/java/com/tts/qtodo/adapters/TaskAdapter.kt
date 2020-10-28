package com.tts.qtodo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tts.qtodo.R
import com.tts.qtodo.models.Priority
import com.tts.qtodo.models.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    private var onItemClickListener: ((Task) -> Unit)? = null

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = differ.currentList[position]
        holder.itemView.apply {
            tv_task_title.text = task.title
            tv_task_description.text = task.description
            when(task.priority) {
                Priority.HIGH -> iv_task_priority.setImageResource(R.drawable.high_priority_circle)
                Priority.MIDDLE -> iv_task_priority.setImageResource(R.drawable.middle_priority_circle)
                Priority.LOW -> iv_task_priority.setImageResource(R.drawable.low_priority_circle)
            }
            setOnClickListener {
                onItemClickListener?.let { it(task) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener: (Task) -> Unit) {
        onItemClickListener = listener
    }

}