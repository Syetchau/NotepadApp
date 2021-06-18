package com.example.notepad.repository

import com.example.notepad.db.NoteDatabase
import com.example.notepad.model.Note

class NoteRepository(private val db: NoteDatabase) {

    suspend fun addNote(note: Note) = db.getNoteDao().addNote(note)

    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)

    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)

    fun getAllNotes() = db.getNoteDao().getAllNotes()

    fun searchNotes(text: String) = db.getNoteDao().searchNote(text)
}