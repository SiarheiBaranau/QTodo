package com.tts.qtodo.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tts.qtodo.R
import com.tts.qtodo.adapters.TaskAdapter
import com.tts.qtodo.ui.MainActivity
import com.tts.qtodo.ui.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_task_list.*

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerview(view)
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

    private fun setupRecyclerview(view: View) {
        taskAdapter = TaskAdapter()
        rv_tasks.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = taskAdapter
            setHasFixedSize(true)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = taskAdapter.differ.currentList[position]
                viewModel.deleteTask(task)
                Snackbar.make(view, "Successfully deleted task", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.insertTask(task)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_tasks)
        }
    }

}