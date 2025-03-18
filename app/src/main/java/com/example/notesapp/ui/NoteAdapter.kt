package com.example.notesapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.model.Note


class NoteAdapter (

    private var notes:List<Note>,
    private var onItemClick:(Note)->Unit

): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    inner class NoteViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        var title:TextView=itemView.findViewById(R.id.noteTitle)
        var description:TextView=itemView.findViewById(R.id.noteDescription)
        init {
            itemView.setOnClickListener{
                onItemClick(notes[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.item_note ,parent,false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder:NoteViewHolder, position: Int) {
      holder.title.text=notes[position].title
      holder.description.text=notes[position].description
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateData(newNotes:List<Note>) {
        notes=newNotes
        notifyDataSetChanged()
    }



}