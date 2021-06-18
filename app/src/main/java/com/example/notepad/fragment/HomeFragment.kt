package com.example.notepad.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notepad.MainActivity
import com.example.notepad.R
import com.example.notepad.adapter.NoteAdapter
import com.example.notepad.databinding.FragmentHomeBinding
import com.example.notepad.model.Note
import com.example.notepad.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding?= null
    private val binding get() = _binding!!
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)

        val menuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = true
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel
        setupRecyclerView()
        initEvents()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchNote(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchNote(newText)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initEvents() {
        binding.fabAdd.setOnClickListener { mView ->
            mView.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter()

        binding.rvMain.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity?.let {
            noteViewModel.getAllNotes().observe(viewLifecycleOwner, { note ->
                noteAdapter.differ.submitList(note)
                updateUI(note)
            })
        }
    }

    private fun updateUI(note: List<Note>) {
        if (note.isNotEmpty()) {
            binding.rvMain.visibility = View.VISIBLE
            binding.tvNoNotes.visibility = View.GONE
        } else{
            binding.rvMain.visibility = View.GONE
            binding.tvNoNotes.visibility = View.VISIBLE
        }
    }

    private fun searchNote(text: String?) {
        val searchText = "%$text%"
        noteViewModel.searchNote(searchText).observe(this, { list ->
            noteAdapter.differ.submitList(list)
        })
    }
}