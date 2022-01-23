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

    var todoNotesDataList = emptyList<ToDoNotesData>()
  private lateinit var binding : RowLayoutBinding

    class NotesListViewHolder(val binding : RowLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListViewHolder {
        binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesListViewHolder, position: Int) {
      holder.binding.titleTxt.text = todoNotesDataList[position].title
      holder.binding.titleDescription.text = todoNotesDataList[position].description
      holder.binding.rowBackground.setOnClickListener {
          val action = NotesListFragmentDirections.actionNotesListFragmentToUpdateNotesFragment(todoNotesDataList[position])
          holder.itemView.findNavController().navigate(action)
      }

        when (todoNotesDataList[position].priority) {
            Priority.HIGH -> holder.binding.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red
                )
            )
            Priority.MEDIUM -> holder.binding.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.yellow
                )
            )
            Priority.LOW -> holder.binding.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.green
                )
            )
        }

    }

    override fun getItemCount(): Int {
        return todoNotesDataList.size
    }

    fun setData(todoNotesData: List<ToDoNotesData>) {
        this.todoNotesDataList = todoNotesData
        notifyDataSetChanged()
    }
}