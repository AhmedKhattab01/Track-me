package com.example.trackme.ui.screens.todo.task

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.google.android.gms.maps.GoogleMap
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

        val bottomAppBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)

        val adapter = TaskAdapter(taskViewModel, listViewModel, args.taskList)
        binding.rvTasks.adapter = adapter

        binding.taskList = args.taskList

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync { map ->
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
                return@getMapAsync
            }

            setupMap(map)
        }

        bottomAppBar.replaceMenu(R.menu.menu_task)
        bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_task_filter -> findNavController().navigate(R.id.action_taskFragment_to_listFilterFragment)
                R.id.action_task_sort -> findNavController().navigate(R.id.action_taskFragment_to_listFilterFragment)
            }

            true
        }

        lifecycleScope.launchWhenCreated {
            combine(
                taskViewModel.getTasks(args.taskList.name),
                taskViewModel.currentFilter
            ) { tasks, _ ->
                taskViewModel.sortTasks(tasks)
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

        binding.ivIcon.setColorFilter(args.taskList.color)
        binding.ivIcon.setImageDrawable(
            iconViewModel.iconPack.getIconDrawable(
                args.taskList.iconId,
                iconViewModel.drawableLoader
            )
        )

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

    @SuppressLint("MissingPermission")
    fun setupMap(map: GoogleMap) {
        with(map) {
            isMyLocationEnabled = true
            with(uiSettings) {
                isScrollGesturesEnabled = false
                isTiltGesturesEnabled = false
                isMyLocationButtonEnabled = false
                isZoomGesturesEnabled = false
                isZoomControlsEnabled = true
            }
            setOnMapClickListener {
                findNavController().navigate(
                    TaskFragmentDirections.actionTaskFragmentToMapsFragment(
                        args.taskList
                    )
                )
            }
        }

        if (args.taskList.latitude != 0.0 && args.taskList.longitude != 0.0) {
            val latLng = LatLng(args.taskList.latitude, args.taskList.longitude)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f))
            map.addMarker(MarkerOptions().position(latLng))

            return
        }

        val locationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        // Get the last known location of the user and move the camera to that location
        locationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val latLng = LatLng(location.latitude, location.longitude)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f))
            }
        }
    }

    // Add code to update the map with the user's current location after the permission is granted
    @SuppressLint("MissingPermission")
    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {

                // Get the map object and move the camera to the user's current location
                mapView.getMapAsync { map ->
                    setupMap(map)
                }
            }
        }
}