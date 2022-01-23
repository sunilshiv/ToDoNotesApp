package com.todo.notesapp.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.todo.notesapp.R
import com.todo.notesapp.data.viewmodel.ToDoNotesViewModel
import com.todo.notesapp.databinding.FragmentNotesListBinding
import com.todo.notesapp.fragments.SharedViewModel

class NotesListFragment : Fragment() {

    private lateinit var binding : FragmentNotesListBinding
    private val adapter: NotesListAdapter by lazy { NotesListAdapter() }
    private val toDoNotesViewModel: ToDoNotesViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        /*val view  =  inflater.inflate(R.layout.fragment_notes_list, container, false)*/
        /* view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
             findNavController().navigate(R.id.action_notesListFragment_to_addNotesFragment)
         }*/
        binding = FragmentNotesListBinding.inflate(inflater, container, false)

        val recyclerView = binding.notesListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        toDoNotesViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
        })

        binding.apply {
            floatingActionButton.setOnClickListener {
                findNavController().navigate(R.id.action_notesListFragment_to_addNotesFragment)
            }

            notesListFragmentLayout.setOnClickListener {
                findNavController().navigate(R.id.action_notesListFragment_to_updateNotesFragment)
            }
        }

        // set menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_list_fragment_menu, menu)
    }
}