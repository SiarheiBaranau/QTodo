package com.tts.qtodo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tts.qtodo.R
import com.tts.qtodo.db.TaskDatabase
import com.tts.qtodo.repositories.TaskRepository
import com.tts.qtodo.ui.viewmodel.TaskViewModel
import com.tts.qtodo.ui.viewmodel.TaskViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskRepository = TaskRepository(TaskDatabase(this))
        val viewModelProviderFactory = TaskViewModelProviderFactory(taskRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(TaskViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all_tasks -> viewModel.deleteAllTasks()
        }
        return super.onOptionsItemSelected(item)
    }

}