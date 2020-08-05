package com.example.kotlintest.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintest.R
import com.example.kotlintest.foundations.BaseRecyclerAdapter
import com.example.kotlintest.models.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter (
    notes: MutableList<Note> = mutableListOf()
): BaseRecyclerAdapter<Note>(notes) {

    /*
    * 1. view holder 정의
    * 2. on create view holder - 이때, item xml 생성
    * 3. on bind view holder - item xml 에 data binding
    * 4. item count 정의
    * */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    )

    class ViewHolder(view: View) : BaseViewHolder<Note>(view) {
        override fun onBind(data: Note) {
            view.descriptionView.text = data.description
        }
    }
}