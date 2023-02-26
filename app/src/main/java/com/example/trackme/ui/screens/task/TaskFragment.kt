package com.example.trackme.ui.screens.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.trackme.R
import com.example.trackme.databinding.FragmentTaskBinding
import com.example.trackme.ui.utils.adapters.TaskAdapter
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)

        val bottomAppBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)

        val adapter = TaskAdapter(taskViewModel)
        val completedAdapter = TaskAdapter(taskViewModel)

        with(binding) {
            // Initialize recyclerview adapters
            rvTasks.adapter = adapter
            rvCompletedTasks.adapter = completedAdapter

            // Set completed task recyclerview visibility depends on button click
            btnCompleted.setOnClickListener {
                if (rvCompletedTasks.isVisible) {
                    scrollView.postDelayed({
                        rvCompletedTasks.visibility = View.GONE
                    }, 300)
                }
                else {
                    rvCompletedTasks.visibility = View.VISIBLE
                    scrollView.postDelayed({
                        scrollView.smoothScrollTo(0, binding.btnCompleted.top)
                    }, 200)
                }
            }
        }

        bottomAppBar.menu.clear()

        lifecycleScope.launchWhenCreated {
            taskViewModel.tasks.collect { task ->
                adapter.submitList(task.filter { !it.isCompleted })
                if (task.none { it.isCompleted }) {
                    binding.btnCompleted.visibility = View.GONE
                }
                else {
                    completedAdapter.submitList(task.filter { it.isCompleted })
                    binding.btnCompleted.visibility = View.VISIBLE
                }
            }
        }

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_taskFragment_to_taskAddFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}