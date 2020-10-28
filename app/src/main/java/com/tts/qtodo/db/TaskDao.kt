package com.tts.qtodo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tts.qtodo.models.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAll(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("DELETE FROM task")
    suspend fun deleteAll()

}