package com.example.notesapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.notesapp.R
import com.example.notesapp.model.Note

class MainActivity : AppCompatActivity() {
    private val noteViewModel:NoteViewModel by viewModels()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var titleInput=findViewById<EditText>(R.id.titleInput)
        val descriptionInput=findViewById<EditText>(R.id.descriptionInput)
        val addButton=findViewById<Button>(R.id.addButton)
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)

         adapter=NoteAdapter(emptyList()){note->
             titleInput.setText(note.title)
             descriptionInput.setText(note.description)
             addButton.setOnClickListener{
                 val updateNote=note.copy(
                     title = titleInput.text.toString(),
                     description=descriptionInput.text.toString()
                 )
                 noteViewModel.update(updateNote)
             }
         }
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)
        noteViewModel.allNotes.observe(this){
            notes->
            adapter.updateData(notes)
        }
        addButton.setOnClickListener{
            val title=titleInput.text.toString()
            val description=descriptionInput.text.toString()
            if(title.isNotEmpty() && description.isNotEmpty()){
                noteViewModel.insert(Note(
                    title = title, description = description))
                titleInput.text.clear()
                descriptionInput.text.clear()

            }
        }
    }
}