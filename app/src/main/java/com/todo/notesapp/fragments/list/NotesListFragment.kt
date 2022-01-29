package com.todo.notesapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.todo.notesapp.R
import com.todo.notesapp.data.models.ToDoNotesData
import com.todo.notesapp.data.viewmodel.ToDoNotesViewModel
import com.todo.notesapp.databinding.FragmentNotesListBinding
import com.todo.notesapp.fragments.SharedViewModel
import com.todo.notesapp.fragments.list.adapter.NotesListAdapter

class NotesListFragment : Fragment() {

    private var _binding : FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private val adapter: NotesListAdapter by lazy { NotesListAdapter() }
    private val mToDoNotesViewModel: ToDoNotesViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Data binding
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        //Setup recyclerview
        createRecyclerView()

        //observe LiveData
        mToDoNotesViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        // set menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun createRecyclerView() {
        val recyclerView = binding.notesListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        //Swipe to delete
        onSwipeToDelete(recyclerView)
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
            mToDoNotesViewModel.deleteAll()
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

    private fun onSwipeToDelete(recyclerView: RecyclerView) {

        val swipeToDeleteCallback = object: SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.todoNotesDataList[viewHolder.adapterPosition]
                //Delete item
                mToDoNotesViewModel.deleteItem(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                // restore item
                restoreDeletedData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    private fun restoreDeletedData(view: View, deletedItem: ToDoNotesData, position: Int) {
        val snackBar = Snackbar.make(
            view,
            "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            mToDoNotesViewModel.insertData(deletedItem)
            adapter.notifyItemChanged(position)
        }
        snackBar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}