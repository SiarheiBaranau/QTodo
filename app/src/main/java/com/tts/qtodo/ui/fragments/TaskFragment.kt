package com.tts.qtodo.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tts.qtodo.R
import com.tts.qtodo.models.Priority
import com.tts.qtodo.models.Task
import com.tts.qtodo.ui.MainActivity
import com.tts.qtodo.ui.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_task.*
import java.util.*

class TaskFragment : Fragment(R.layout.fragment_task) {

    private lateinit var viewModel: TaskViewModel
    private val args: TaskFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel

        val currentTask = args.task
        currentTask?.let {
            et_task_title.setText(it.title)
            when (it.priority) {
                Priority.HIGH -> sp_priority.setSelection(2)
                Priority.MIDDLE -> sp_priority.setSelection(1)
                Priority.LOW -> sp_priority.setSelection(0)
            }
            et_task_description.setText(it.description)
            btn_delete_task.visibility = View.VISIBLE
        }

        btn_delete_task.setOnClickListener {
            if (currentTask != null) {
                viewModel.deleteTask(currentTask)
                findNavController().navigateUp()
            }
        }

        btn_save_task.setOnClickListener {
            val title = et_task_title.text.toString().trim()
            val description = et_task_description.text.toString().trim()
            val priority =
                Priority.valueOf(sp_priority.selectedItem.toString().toUpperCase(Locale.ROOT))
            val task = Task(null, title, description, priority)
            currentTask?.let {
                task.id = it.id
            }

            when {
                title.isEmpty() -> {
                    et_task_title.error = "Title is not valid!"
                }
                description.isEmpty() -> {
                    et_task_description.error = "Description is not valid!"
                }
                else -> {
                    if (currentTask != null) {
                        viewModel.updateTask(task)
                    } else {
                        viewModel.insertTask(task)
                    }
                    val imm =
                        (activity as MainActivity).getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    findNavController().navigateUp()
                }
            }
        }
    }

}