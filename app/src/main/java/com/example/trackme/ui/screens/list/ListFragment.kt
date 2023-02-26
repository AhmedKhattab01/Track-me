package com.example.trackme.ui.screens.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.trackme.R
import com.example.trackme.databinding.FragmentListBinding
import com.example.trackme.ui.utils.adapters.TaskListAdapter
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.combine

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val listViewModel: ListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)

        // Create adapter
        val adapter = TaskListAdapter(listViewModel)
        binding.rvLists.adapter = adapter

        // Initialize activity views
        val bottomAppBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)

        // Replace current menu with this fragment menu
        bottomAppBar.replaceMenu(R.menu.menu_list)
        bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_list_sort -> findNavController().navigate(R.id.action_listFragment_to_listFilterFragment)
            }

            return@setOnMenuItemClickListener true
        }

        // collect list items
        lifecycleScope.launchWhenCreated {
            listViewModel.list.combine(listViewModel.filter) { list, filter ->
                when (filter) {
                    1 -> list.sortedByDescending { it.name }.reversed()// Ascending
                    2 -> list.sortedByDescending { it.name }// Descending
                    else -> list
                }
            }.collect { filteredList ->
                adapter.submitList(filteredList)
                setVisibility(filteredList.isEmpty())
            }
        }


        // launch bottom sheet on fab clicked
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_listAddFragment)
        }

        // Delete list on swipe
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]

                listViewModel.deleteList(item)

                Snackbar.make(binding.root, "Successfully Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") { listViewModel.insertList(item) }
                    .show()
            }
        }).attachToRecyclerView(binding.rvLists)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setVisibility(isEmpty :Boolean) {
        val visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.imageView.visibility = visibility
        binding.textView.visibility = visibility
        binding.textView2.visibility = visibility
    }
}