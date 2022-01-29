package com.todo.notesapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.todo.notesapp.R
import com.todo.notesapp.data.models.Priority
import com.todo.notesapp.data.models.ToDoNotesData
import com.todo.notesapp.databinding.RowLayoutBinding

class NotesListAdapter: RecyclerView.Adapter<NotesListAdapter.NotesListViewHolder>() {

    private var todoNotesDataList = emptyList<ToDoNotesData>()
    private lateinit var binding : RowLayoutBinding

    class NotesListViewHolder(private val binding : RowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(toDoNotesData: ToDoNotesData) {
            binding.todoNotesData = toDoNotesData
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NotesListViewHolder {

                val layoutInflater =  LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater, parent, false)
                return NotesListViewHolder(binding)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListViewHolder {
        return NotesListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NotesListViewHolder, position: Int) {
     val currentItem = todoNotesDataList[position]
     holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return todoNotesDataList.size
    }

    fun setData(todoNotesData: List<ToDoNotesData>) {
        this.todoNotesDataList = todoNotesData
        notifyDataSetChanged()
    }
}