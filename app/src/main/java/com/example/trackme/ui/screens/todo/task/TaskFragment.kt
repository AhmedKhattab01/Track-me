package com.example.trackme.ui.screens.todo.task

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.trackme.R
import com.example.trackme.databinding.FragmentTaskBinding
import com.example.trackme.ui.core.adapters.TaskAdapter
import com.example.trackme.ui.screens.todo.tasks_lists.ListViewModel
import com.example.trackme.ui.shared_viewmodels.IconViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.combine

class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by activityViewModels()
    private val listViewModel: ListViewModel by activityViewModels()
    private val iconViewModel: IconViewModel by activityViewModels()

    private val args by navArgs<TaskFragmentArgs>()

    private lateinit var mapView: MapView

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync { map ->
            map.isMyLocationEnabled = true
            map.uiSettings.isScrollGesturesEnabled = false
            map.uiSettings.isTiltGesturesEnabled = false
            map.uiSettings.isMyLocationButtonEnabled = false

            val location = LocationServices.getFusedLocationProviderClient(requireContext())

            if (args.taskList.latitude == 0.0 && args.taskList.longitude == 0.0) {
                location.lastLocation.addOnSuccessListener {
                    val lat = it.latitude
                    val long = it.longitude
                    val latLng = LatLng(lat, long)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f))
                }
            }
            else {
                val latLng = LatLng(args.taskList.latitude, args.taskList.longitude)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f))
                map.addMarker(MarkerOptions().position(latLng))
            }
            map.setOnMapClickListener {
                findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToMapsFragment(args.taskList))
            }
        }

        val bottomAppBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)

        val adapter = TaskAdapter(taskViewModel, listViewModel, args.taskList)

        with(binding) {
            taskList = args.taskList

            // Initialize recyclerview adapters
            rvTasks.adapter = adapter
        }

        bottomAppBar.replaceMenu(R.menu.menu_task)
        bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_task_filter -> findNavController().navigate(R.id.action_taskFragment_to_listFilterFragment)
                R.id.action_task_sort -> findNavController().navigate(R.id.action_taskFragment_to_listFilterFragment)
            }

            return@setOnMenuItemClickListener true
        }

        lifecycleScope.launchWhenCreated {
            combine(
                taskViewModel.getTasks(args.taskList.name),
                taskViewModel.currentFilter
            ) { tasks, filter ->
                when (filter) {
                    1 -> tasks.sortedBy { it.title } // Ascending
                    2 -> tasks.sortedByDescending { it.title } // Descending
                    else -> tasks
                }
            }.collect { filteredList ->
                adapter.submitList(filteredList)
            }
        }

        fab.setOnClickListener {
            findNavController().navigate(
                TaskFragmentDirections.actionTaskFragmentToTaskAddFragment(
                    args.taskList
                )
            )
        }

        binding.ivIcon.setImageDrawable(
            iconViewModel.iconPack.getIconDrawable(
                args.taskList.iconId,
                iconViewModel.drawableLoader
            )
        )

        binding.ivIcon.setColorFilter(args.taskList.color)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}