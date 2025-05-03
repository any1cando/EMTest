package com.panevrn.emtest.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.panevrn.emtest.R
import com.panevrn.emtest.databinding.FragmentMainBinding
import com.panevrn.emtest.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = childFragmentManager.findFragmentById(R.id.fragmentContainerMain)?.findNavController() ?: findNavController()
        binding.bottomNavigationMenu.setupWithNavController(navController)

        // ViewModel будет хранить выбранный экран, который сейчас отображается
        // TODO: Возможно, удалить ViewModel из-за ненадобности
        binding.bottomNavigationMenu.setOnItemSelectedListener { item ->
            viewModel.onIconSelected(item.itemId)
            false
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}