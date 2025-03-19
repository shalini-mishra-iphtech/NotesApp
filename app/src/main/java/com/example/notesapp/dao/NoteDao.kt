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
  suspend  fun insert(note:Note)

    @Update
   suspend fun update(note:Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note_table ORDER BY id ")
    suspend fun getAllNotes():List<Note>
}