package com.example.trackme.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.common_ui.Utils.visibleIf
import com.example.trackme.R
import com.example.trackme.TrackApplication
import com.example.trackme.databinding.FragmentHomeBinding
import com.example.trackme.ui.home.adapters.TaskAdapter
import com.example.trackme.ui.home.tasks.TasksSortingSheet
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.maltaisn.icondialog.pack.IconPackLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), MenuProvider {
    private val TAG = this.javaClass.simpleName

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    private val viewModel: HomeViewModel by viewModels(ownerProducer = { this })
    private val loader by lazy { IconPackLoader(requireContext()) }

    private val taskAdapter by lazy {
        TaskAdapter(
            loader.drawableLoader,
            (requireActivity().application as TrackApplication).iconPack
        )
    }

    private val dialog by lazy { TasksSortingSheet() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvTasks.adapter = taskAdapter

        if (firebaseAuth.currentUser == null) {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                lifecycleScope.launch {
                    val task = taskAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.deleteTask(task)

                    Snackbar.make(
                        requireView(),
                        getString(R.string.task_deleted), Snackbar.LENGTH_LONG
                    ).setAction(getString(R.string.undo)) {
                        viewModel.insertTask(task)
                    }.show()
                }
            }
        }).attachToRecyclerView(binding.rvTasks)

        observeTasks()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun observeTasks() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tasks.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                binding.grpEmptyTasks.visibleIf((it).isEmpty())
                taskAdapter.submitList(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_sorting -> {
                dialog.show(childFragmentManager, TAG)
                true
            }

            else -> true
        }
    }
}