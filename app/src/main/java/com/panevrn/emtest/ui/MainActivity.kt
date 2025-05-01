package com.panevrn.emtest.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.panevrn.emtest.R
import com.panevrn.emtest.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

//        val navController = findNavController(R.id.nav_host_fragment)
//
//        viewModel.startDestination.observe(this) { destination ->
//            val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
//            navGraph.setStartDestination(destination)
//            navController.graph = navGraph
//        }
    }
}