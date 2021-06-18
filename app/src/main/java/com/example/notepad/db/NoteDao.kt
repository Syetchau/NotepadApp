package com.example.notepad.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notepad.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE noteTitle LIKE :text OR noteBody LIKE :text")
    fun searchNote(text: String?): LiveData<List<Note>>
}