package com.panevrn.emtest.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.panevrn.emtest.R
import com.panevrn.emtest.databinding.FragmentCoursesBinding
import com.panevrn.emtest.databinding.FragmentFavoritesCoursesBinding


class FavoritesCoursesFragment : Fragment() {

    private var _binding: FragmentFavoritesCoursesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoritesCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}