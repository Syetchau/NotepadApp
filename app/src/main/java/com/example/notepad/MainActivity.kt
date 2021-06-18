package com.example.notepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notepad.databinding.ActivityMainBinding
import com.example.notepad.db.NoteDatabase
import com.example.notepad.repository.NoteRepository
import com.example.notepad.viewmodel.NoteViewModel
import com.example.notepad.viewmodel.NoteViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbMain)

        setupViewModel()
    }

    private fun setupViewModel() {

        val noteRepo = NoteRepository(NoteDatabase(this))
        val viewModelProvideFactory = NoteViewModelProviderFactory(application, noteRepo)
        noteViewModel = ViewModelProvider(this, viewModelProvideFactory)
            .get(NoteViewModel::class.java)
    }
}