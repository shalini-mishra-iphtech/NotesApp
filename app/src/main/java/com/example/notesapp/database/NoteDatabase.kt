package com.example.notesapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.dao.NoteDao
import com.example.notesapp.model.Note


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase :RoomDatabase(){
    abstract fun noteDao():NoteDao

    companion object{
        @Volatile
        private var INSTANCE:NoteDatabase?= null


        fun getInstance(context:Context):NoteDatabase{
            Log.d("NoteDatabase", "Initializing database")

            return INSTANCE?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "Note_database"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE=instance
                instance
            }
        }
    }
}


