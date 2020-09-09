package br.com.fiap.roomdatabase.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.fiap.roomdatabase.task.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    suspend fun getTasks() : List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(vararg task: Task)

    @Query("DELETE FROM task")
    suspend fun deleteAll()
}