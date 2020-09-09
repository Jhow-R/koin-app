package br.com.fiap.roomdatabase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.fiap.roomdatabase.task.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao() : TaskDao
}