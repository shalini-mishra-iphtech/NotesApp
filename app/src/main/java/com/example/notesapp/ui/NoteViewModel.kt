package com.example.notesapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteViewModel (application: Application) :AndroidViewModel(application){
    private val noteDao=NoteDatabase.getInstance(application).noteDao()
    val allNotes=MutableLiveData<List<Note>>()
    init {
        loadNotes()
        Log.d("NoteViewModel","ViewModel initialized")
    }


    //load notes from data bases using coroutine concept

     private fun loadNotes(){
         viewModelScope.launch (Dispatchers.IO){
             val notes=noteDao.getAllNotes()
             allNotes.postValue(notes)
             Log.d("NoteViewModel","Notes loaded:${notes.size}")

         }
     }




    //Insert data using Coroutine concept
    fun insert(note: Note){
        viewModelScope.launch (Dispatchers.IO){
            Log.d("NoteViewModel","Inserting note:$note")
            noteDao.insert(note)
            loadNotes()
        }
    }


    fun update(note:Note){
        viewModelScope.launch (Dispatchers.IO){
            Log.d("NoteViewModel","Updating note:$note")
            noteDao.update(note)
            loadNotes()
        }
    }

    fun delete(note: Note){
        viewModelScope.launch (Dispatchers.IO){
            Log.d("NoteViewModel","Deleting note:$note")
            noteDao.delete(note)
            loadNotes()
        }
    }
}