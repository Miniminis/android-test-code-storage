package com.example.kotlintest.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintest.R
import com.example.kotlintest.foundations.BaseRecyclerAdapter
import com.example.kotlintest.models.Task
import com.example.kotlintest.views.TodoView
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter(
    taskList: MutableList<Task> = mutableListOf()
): BaseRecyclerAdapter<Task>(taskList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))

    class ViewHolder(view: View): BaseViewHolder<Task>(view) {

        override fun onBind(data: Task) {
            view.titleView.text = data.title

            data.todos.forEach {todo ->
                val todoView = (LayoutInflater.from(view.context).inflate(R.layout.view_todo, view.todoContainer, false) as TodoView).apply {
                    initView(todo)
                }
                view.todoContainer.addView(todoView)
            }
        }
    }

}
