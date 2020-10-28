package com.tts.qtodo.repositories

import com.tts.qtodo.db.TaskDatabase
import com.tts.qtodo.models.Task

class TaskRepository(private val db: TaskDatabase) {

    fun getAllTask() = db.getTaskDao().getAll()

    suspend fun insertTask(task: Task) = db.getTaskDao().insert(task)

    suspend fun deleteTask(task: Task) = db.getTaskDao().delete(task)

    suspend fun updateTask(task: Task) = db.getTaskDao().update(task)

    suspend fun deleteAll() = db.getTaskDao().deleteAll()

}