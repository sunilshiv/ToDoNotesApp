package com.todo.notesapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.todo.notesapp.R
import com.todo.notesapp.data.models.Priority
import com.todo.notesapp.data.models.ToDoNotesData
import com.todo.notesapp.data.viewmodel.ToDoNotesViewModel
import com.todo.notesapp.databinding.FragmentAddNotesBinding
import com.todo.notesapp.databinding.FragmentNotesListBinding
import com.todo.notesapp.fragments.SharedViewModel

class AddNotesFragment : Fragment() {

    private lateinit var binding : FragmentAddNotesBinding
    private val mTodoNotesViewModel: ToDoNotesViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //set menu
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        binding = FragmentAddNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add_notes) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
       val title = binding.edtAddNotesTitle.text.toString()
       val priority = binding.spnAddNotesSpinner.selectedItem.toString()
       val description = binding.edtAddNotesDescription.text.toString()
       val validation = mSharedViewModel.verifyDataFromUser(title, description)

        if (validation) {
            //Insert data to database.
            val newData = ToDoNotesData(
                0,
                title,
                mSharedViewModel.parsePriority(priority),
                description
            )
            mTodoNotesViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successful data inserted", Toast.LENGTH_SHORT).show()
            //Navigate back
            findNavController().navigate(R.id.action_addNotesFragment_to_notesListFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }

    }

}