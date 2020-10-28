package com.tts.qtodo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tts.qtodo.models.Task
import com.tts.qtodo.repositories.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    lateinit var tasks: LiveData<List<Task>>

    init {
        getAllTasks()
    }

    private fun getAllTasks() = viewModelScope.launch {
        tasks = repository.getAllTask()
    }

    fun insertTask(task: Task) = viewModelScope.launch {
        repository.insertTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }

    fun deleteAllTasks() = viewModelScope.launch {
        repository.deleteAll()
    }

}