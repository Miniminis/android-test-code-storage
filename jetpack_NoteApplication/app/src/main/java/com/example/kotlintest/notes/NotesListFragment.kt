package com.example.kotlintest.notes


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlintest.R
import com.example.kotlintest.models.Note
import kotlinx.android.synthetic.main.fragment_notes_list.*

/**
 * A simple [Fragment] subclass.
 */
class NotesListFragment : Fragment() {

    companion object {
        fun newInstance() = NotesListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        var  adapter = NoteAdapter(
            mutableListOf(
                Note("pi is not exactly 3.14"),
                Note("A double double is Canadian for coffee two cream two sugar")
            )
        )
        recyclerView.adapter = adapter
    }
}
