package com.example.notepad.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.notepad.MainActivity
import com.example.notepad.R
import com.example.notepad.databinding.FragmentNewNoteBinding
import com.example.notepad.model.Note
import com.example.notepad.toast
import com.example.notepad.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar

class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    private var _binding: FragmentNewNoteBinding?= null
    private val binding get() = _binding!!
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.new_note_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel
        mView = view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.save_menu -> {
                saveNote(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun saveNote(view: View) {

        val noteTitle = binding.etNoteTitle.text.toString().trim()
        val noteBody = binding.etNoteBody.text.toString().trim()

        if(noteTitle.isNotEmpty()) {
            val note = Note(0, noteTitle, noteBody)
            noteViewModel.addNote(note)
            Snackbar.make(view, "Note added successfully", Snackbar.LENGTH_LONG).show()
            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
        } else {
            activity?.toast("Please enter note title!")
        }
    }
}