package com.example.trackme.ui.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.trackme.R
import com.example.trackme.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Places.initialize(this,"AIzaSyDolYK9la0uJgVjB5Cmq42rGPofZiVZkgM")
//
//        val x = binding.button as AutocompleteSupportFragment
//
//        x.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
//
//        x.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            override fun onError(p0: Status) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onPlaceSelected(p0: Place) {
//                TODO("Not yet implemented")
//            }
//
//        })

        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        // initialize controller
        navController = navHostFragment.navController


        binding.bottomAppBar
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}