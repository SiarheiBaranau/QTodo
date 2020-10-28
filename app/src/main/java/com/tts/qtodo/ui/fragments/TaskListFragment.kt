package com.tts.qtodo.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tts.qtodo.R
import com.tts.qtodo.adapters.TaskAdapter
import com.tts.qtodo.ui.MainActivity
import com.tts.qtodo.ui.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_task_list.*

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerview()
        viewModel = (activity as MainActivity).viewModel

        viewModel.tasks.observe(viewLifecycleOwner, {
            taskAdapter.differ.submitList(it)
        })

        taskAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putSerializable("task", it)
            findNavController().navigate(R.id.action_taskListFragment_to_taskFragment, bundle)
        }

        fab_create_task.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_taskFragment)
        }
    }

    private fun setupRecyclerview() {
        taskAdapter = TaskAdapter()
        rv_tasks.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = taskAdapter
            setHasFixedSize(true)
        }
    }

}