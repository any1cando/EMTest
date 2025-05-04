package com.panevrn.emtest.ui.main.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.panevrn.domain.model.CourseModel
import com.panevrn.emtest.databinding.ItemCourseBinding

class CoursesAdapter(
    private val onLikeClick: (CourseModel) -> Unit,
    private val onItemClick: (CourseModel) -> Unit
) : ListAdapter<CourseModel, CourseViewHolder>(CourseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding, onLikeClick, onItemClick)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class CourseDiffCallback : DiffUtil.ItemCallback<CourseModel>() {
    override fun areItemsTheSame(oldItem: CourseModel, newItem: CourseModel) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: CourseModel, newItem: CourseModel) = oldItem == newItem
}