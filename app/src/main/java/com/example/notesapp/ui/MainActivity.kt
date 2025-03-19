package com.example.notesapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.model.Note

class MainActivity : AppCompatActivity() {
    private lateinit var noteViewModel:NoteViewModel
    private lateinit var adapter: NoteAdapter
    private var currentlyEditingNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("MainActivity", "Activity created")
        noteViewModel=ViewModelProvider(this)[NoteViewModel
        ::class.java]
        Log.d("MainActivity", "ViewModel initialized")

        val titleInput=findViewById<EditText>(R.id.titleInput)
        val descriptionInput=findViewById<EditText>(R.id.descriptionInput)
        val addButton=findViewById<Button>(R.id.addButton)
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)


        adapter = NoteAdapter(
            notes = mutableListOf(),
            onEditClick = { note ->
                // Populate fields with selected note for editing
                titleInput.setText(note.title)
                descriptionInput.setText(note.description)
                currentlyEditingNote = note
                addButton.text = "Update Note"
            },
            onDeleteClick = { note ->
                noteViewModel.delete(note)
            }
        )
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)
        noteViewModel.allNotes.observe(this){
            notes->
            Log.d("MainActivity", "Received notes from ViewModel: ${notes.size}")
            adapter.updateData(notes)
        }

        // Handle Insert/Update
        addButton.setOnClickListener {
            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                if (currentlyEditingNote == null) {
                    // Insert new note
                    noteViewModel.insert(Note(title = title, description = description))
                } else {
                    // Update existing note
                    val updatedNote = currentlyEditingNote!!.copy(
                        title = title,
                        description = description
                    )
                    noteViewModel.update(updatedNote)
                    currentlyEditingNote = null
                    addButton.text = "Add Note"
                }

                // Clear input fields
                titleInput.text.clear()
                descriptionInput.text.clear()
            }
        }

    }
}