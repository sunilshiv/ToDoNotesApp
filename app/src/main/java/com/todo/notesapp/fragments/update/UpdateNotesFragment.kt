package com.todo.notesapp.fragments.update

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.todo.notesapp.R
import com.todo.notesapp.data.models.ToDoNotesData
import com.todo.notesapp.data.viewmodel.ToDoNotesViewModel
import com.todo.notesapp.databinding.FragmentUpdateNotesBinding
import com.todo.notesapp.fragments.SharedViewModel
import com.todo.notesapp.utils.NotesUtils.hideKeyboard

class UpdateNotesFragment : Fragment() {

    private val args by navArgs<UpdateNotesFragmentArgs>()

    private val mSharedViewModel: SharedViewModel by viewModels()
    private val toDoNotesViewModel: ToDoNotesViewModel by viewModels()

    private var _binding: FragmentUpdateNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // set menu
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        _binding = FragmentUpdateNotesBinding.inflate(inflater, container, false)
        binding.args = args

        // Spinner Item Selected Listener
        binding.spnUpdateNotesSpinner.onItemSelectedListener = mSharedViewModel.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_save_notes -> updateItem()
            R.id.menu_delete_notes -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = binding.edtUpdateNotesTitle.text.toString()
        val description = binding.edtAddNotesDescription.text.toString()
        val priority = binding.spnUpdateNotesSpinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if(validation) {
            // update current item
            val updatedItem= ToDoNotesData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(priority),
                description
            )
            toDoNotesViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateNotesFragment_to_notesListFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill the all the items!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _, ->
            toDoNotesViewModel.deleteItem(args.currentItem)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentItem.title}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateNotesFragment_to_notesListFragment)
        }
        builder.setNegativeButton("No") {_,_ -> }
        builder.setTitle("Delete ${args.currentItem.title}?")
        builder.setMessage("Are you sure you want to remove ${args.currentItem.title}?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

}