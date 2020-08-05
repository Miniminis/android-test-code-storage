package com.example.kotlintest.tasks


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlintest.R
import com.example.kotlintest.models.Task
import com.example.kotlintest.models.Todo
import kotlinx.android.synthetic.main.fragment_tasks_list.*

/**
 * A simple [Fragment] subclass.
 */
class TasksListFragment : Fragment() {

    companion object {
        fun newInstance() = TasksListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TaskAdapter(mutableListOf(
            Task("Testing One!", mutableListOf(
                Todo("Test One!", true),
                Todo("Test Two!")
            )),
            Task("Testing Two!")
        ))
        recyclerView.adapter = adapter
    }

}
