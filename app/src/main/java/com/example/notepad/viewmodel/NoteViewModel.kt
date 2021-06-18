package com.example.notepad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.model.Note
import com.example.notepad.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app: Application, private val repo: NoteRepository): AndroidViewModel(app) {

    fun addNote(note: Note) = viewModelScope.launch {
        repo.addNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repo.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repo.deleteNote(note)
    }

    fun getAllNotes() = repo.getAllNotes()

    fun searchNote(text: String) = repo.searchNotes(text)
}