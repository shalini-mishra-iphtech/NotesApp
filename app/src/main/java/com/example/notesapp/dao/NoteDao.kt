package com.example.notesapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notesapp.model.Note

@Dao
interface NoteDao{
    @Insert
    fun insert(note:Note)

    @Update
    fun update(note:Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun getAllNotes():List<Note>
}