package com.example.trackme.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.trackme.R
import com.example.trackme.ui.tasks.TaskViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapsFragment : Fragment() {

    private val taskViewModel: TaskViewModel by activityViewModels()
    private lateinit var mLatLng: LatLng
    private lateinit var fab: FloatingActionButton

//    private val args by navArgs<MapsFragmentArgs>()
    private lateinit var currentLocation : FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { map ->
        // Add current location marker if latitude and longitude are available
//        with(args.taskList) {
//            if (latitude != 0.0 && longitude != 0.0) {
//                mLatLng = LatLng(latitude,longitude)
//                map.addMarker(MarkerOptions().position(mLatLng))
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15.0f))
//            }
//            else {
//                currentLocation.lastLocation.addOnSuccessListener { location ->
//                    mLatLng = LatLng(location.latitude, location.longitude)
//                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15.0f))
//                }
//            }
//        }

        // Add marker on map click and clear existing markers
        map.setOnMapClickListener { latLng ->
            mLatLng = latLng

            map.clear()
            map.addMarker(MarkerOptions().position(latLng))

//            args.taskList.longitude = latLng.longitude
//            args.taskList.latitude = latLng.latitude
//            taskViewModel.updateList(args.taskList)
        }

        // Enable My Location button and zoom controls
        with(map.uiSettings) {
            isMyLocationButtonEnabled = true
            isZoomControlsEnabled = true
        }

        map.isMyLocationEnabled = true

        // Move camera to current location on My Location button click
        map.setOnMyLocationButtonClickListener {
                currentLocation.lastLocation.addOnSuccessListener { location ->
                    val latLng = LatLng(location.latitude, location.longitude)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f))
                }
            true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val bottomAppBar = requireActivity().requireViewById<BottomAppBar>(R.id.bottomAppBar)
//
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
//        mapFragment?.getMapAsync(callback)
//
//        mLatLng = LatLng(args.taskList.latitude, args.taskList.longitude)
//        currentLocation = LocationServices.getFusedLocationProviderClient(requireContext())
//
//        fab = requireActivity().requireViewById<FloatingActionButton>(R.id.fab)
//        fab.hide()
//
//        bottomAppBar.replaceMenu(R.menu.menu_map)
//        bottomAppBar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                else -> false
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fab.show()
    }
}