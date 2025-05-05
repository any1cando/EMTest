package com.panevrn.emtest.ui.main.favoritescourses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.panevrn.emtest.databinding.FragmentFavoritesCoursesBinding
import com.panevrn.emtest.ui.main.common.CoursesAdapter
import com.panevrn.emtest.viewmodel.FavoritesCoursesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesCoursesFragment : Fragment() {

    private val viewModel: FavoritesCoursesViewModel by viewModels()
    private var _binding: FragmentFavoritesCoursesBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoritesCoursesAdapter: CoursesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onResume() {
        super.onResume()
        viewModel.loadFavoritesCourses()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoritesCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.favoritesCourses.observe(viewLifecycleOwner) { courses ->
            favoritesCoursesAdapter.submitList(courses)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupRecyclerView() {
        favoritesCoursesAdapter = CoursesAdapter(
            onLikeClick = { course -> viewModel.toggleLike(course) },
            onItemClick = { course -> viewModel.selectCourse(course) }
        )
        binding.rvFavoritesCourses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoritesCoursesAdapter
        }
    }
}