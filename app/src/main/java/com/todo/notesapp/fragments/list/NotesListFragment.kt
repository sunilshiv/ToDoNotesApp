package com.todo.notesapp.fragments.list

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
        view?.hideKeyboard()
        val recyclerView = binding.notesListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        toDoNotesViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabase(it)
        })

        binding.apply {
            floatingActionButton.setOnClickListener {
                findNavController().navigate(R.id.action_notesListFragment_to_addNotesFragment)
            }

          /*  notesListFragmentLayout.setOnClickListener {
                findNavController().navigate(R.id.action_notesListFragment_to_updateNotesFragment)
            }*/
        }

        // set menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun showEmptyDatabase(emptyDatabase: Boolean) {
       if(emptyDatabase) {
           binding.apply {
               noDataImageview.visibility = View.VISIBLE
               noDataTextView.visibility = View.VISIBLE

           }

       }else{
           binding.apply {
               noDataImageview.visibility = View.INVISIBLE
               noDataTextView.visibility = View.INVISIBLE
           }
       }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete_all) {
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    //Show AlertDialog to confirm removal of All items from the database table
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_,_ ->
            toDoNotesViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Successfully Removed Everything!",
                Toast.LENGTH_SHORT
            ).show()
        }
       builder.setNegativeButton("No") {_,_ ->}
       builder.setTitle("Delete Everything!")
       builder.setMessage("Are you sure you want to delete everything?")
       builder.create().show()
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}