package com.example.notesapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.model.Note


class NoteViewModel (application: Application) :AndroidViewModel(application){
    private val noteDao=NoteDatabase.getInstance(application).noteDao()
    val allNotes=MutableLiveData<List<Note>>()
    init {
        loadNotes()
    }
    private fun loadNotes(){
        allNotes.value=noteDao.getAllNotes()
    }
    fun insert(note: Note){
        noteDao.update(note)
        loadNotes()
    }
    fun update(note:Note){
        noteDao.update(note)
        loadNotes()
    }
    fun delete(note: Note){
        noteDao.delete(note)
        loadNotes()
    }
}