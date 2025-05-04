package com.panevrn.domain.usecase.main.common

import com.panevrn.domain.model.CourseModel
import com.panevrn.domain.repository.CoursesRepository

class ToggleLikeCourseUseCase(private val repository: CoursesRepository) {
    suspend operator fun invoke(course: CourseModel) {
        if (repository.isCourseLiked(course.id)) {
            repository.removeCourseFromFavorites(course.id)
        } else {
            repository.addCourseToFavorites(course)
        }

    }
}