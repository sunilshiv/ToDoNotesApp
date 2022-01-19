package com.todo.notesapp.data

import android.content.Context
import androidx.room.*
import com.todo.notesapp.data.models.ToDoNotesData

@Database(entities = [ToDoNotesData::class], version = 1, exportSchema = false)
@TypeConverters(PriorityConverter::class)
abstract class ToDoNotesDatabase : RoomDatabase() {

    abstract fun toDoNotesDao() : ToDoNotesDao

    /*companion object {
        @Volatile
        private var INSTANCE: ToDoNotesDatabase? = null

        fun getDatabase(context: Context): ToDoNotesDatabase {
            var tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                    tempInstance = Room.databaseBuilder(
                        context.applicationContext,
                    ToDoNotesDatabase::class.java,
                    "todo_notes_database")
                        .build()
                INSTANCE = tempInstance
                return tempInstance as ToDoNotesDatabase
            }
        }


    }*/

    companion object {

        @Volatile
        private var INSTANCE: ToDoNotesDatabase? = null

        fun getInstance(context: Context): ToDoNotesDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ToDoNotesDatabase::class.java,
                        "todo_notes_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}