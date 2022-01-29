package com.todo.notesapp.fragments.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.todo.notesapp.data.models.ToDoNotesData

class ToDoNotesDiffUtils(
    private val oldList: List<ToDoNotesData>,
    private val newList: List<ToDoNotesData>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
       return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldList[oldItemPosition].id == newList[newItemPosition].id
               && oldList[oldItemPosition]. title == newList[newItemPosition].title
               && oldList[oldItemPosition]. description == newList[newItemPosition].description
               && oldList[oldItemPosition]. priority == newList[newItemPosition].priority
    }


}