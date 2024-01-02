package com.slayer.trackme.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.slayer.common_ui.Utils.visibleIf
import com.slayer.trackme.R
import com.slayer.trackme.databinding.ActivityMainBinding
import com.slayer.trackme.ui.home.tasks.TaskAddSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private val destinations = listOf(
        R.id.homeFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.bottomAppBar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomAppBar.setupWithNavController(navController)

        binding.fab.setOnClickListener {
            when (navController.currentDestination?.id) {
                R.id.homeFragment -> {
                    val addTaskSheet = TaskAddSheet()
                    addTaskSheet.show(supportFragmentManager, TAG)
                }
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.apply {
                bottomAppBar.visibleIf(destination.id in destinations)
                fab.visibleIf(destination.id in destinations)
                appBarLayout.visibleIf(destination.id in destinations)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}