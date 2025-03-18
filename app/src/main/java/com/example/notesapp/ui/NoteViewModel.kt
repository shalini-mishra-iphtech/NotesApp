package com.example.notesapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.model.Note


class NoteViewModel (application: Application) :AndroidViewModel(application){
    private val noteDao=NoteDatabase.getInstance(application).noteDao()
    val allNotes=MutableLiveData<List<Note>>()
    init {
        loadNotes()
        Log.d("NoteViewModel","ViewModel initialized")
    }
    private fun loadNotes(){
        Log.d("NoteViewModel", "Loading notes from database")
            // allNotes.value=noteDao.getAllNotes()
        allNotes.postValue(noteDao.getAllNotes())

        Log.d("NoteViewModel", "Notes loaded: ${allNotes.value?.size}")
    }
    fun insert(note: Note){
        Log.d("NoteViewModel", "Inserting note: $note")
       // noteDao.update(note)
        noteDao.insert(note)
        loadNotes()
    }
    fun update(note:Note){
        Log.d("NoteViewModel", "Updating note: $note")
        noteDao.update(note)
        loadNotes()
    }
    fun delete(note: Note){
        Log.d("NoteViewModel", "Deleting note: $note")
        noteDao.delete(note)
        loadNotes()
    }
}