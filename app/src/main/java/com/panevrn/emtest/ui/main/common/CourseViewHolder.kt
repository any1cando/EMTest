package com.panevrn.emtest.ui.main.common

import androidx.recyclerview.widget.RecyclerView
import com.panevrn.domain.model.CourseModel
import com.panevrn.emtest.R
import com.panevrn.emtest.databinding.ItemCourseBinding

class CourseViewHolder(
    private val binding: ItemCourseBinding,
    private val onLikeClick: (CourseModel) -> Unit,
    private val onItemClick: (CourseModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(course: CourseModel) = with(binding) {
        tvTitle.text = course.title
        tvDescription.text = course.text
        tvPrice.text = "${course.price} ₽"
        tvRating.text = course.rate.toString()
        tvDate.text = course.startDate

        ivCourseImage.setImageResource(R.drawable.test_course_image)

        ibFavoriteCourse.setImageResource(
            if (course.hasLike) R.drawable.ic_common_favorite_course_true
            else R.drawable.ic_common_favorite_course_false
        )

        ibFavoriteCourse.setOnClickListener { onItemClick(course) }
        ibFavoriteCourse.setOnClickListener { onLikeClick(course) }
    }
}

