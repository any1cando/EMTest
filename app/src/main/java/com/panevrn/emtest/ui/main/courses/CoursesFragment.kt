package com.panevrn.emtest.ui.main.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.panevrn.emtest.databinding.FragmentCoursesBinding
import com.panevrn.emtest.viewmodel.CoursesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CoursesFragment : Fragment() {

    private val viewModel: CoursesViewModel by viewModels()
    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!
    private lateinit var coursesAdapter: CoursesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            coursesAdapter.submitList(courses)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupRecyclerView() {
        coursesAdapter = CoursesAdapter(
            onLikeClick = { course -> viewModel.toggleLike(course) },
            onItemClick = { course -> viewModel.selectCourse(course) }
        )
        binding.rvCourses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = coursesAdapter
        }
    }
}