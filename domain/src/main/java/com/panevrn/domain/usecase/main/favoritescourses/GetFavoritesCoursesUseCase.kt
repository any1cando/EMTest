package com.panevrn.domain.usecase.main.favoritescourses

import com.panevrn.domain.model.CourseModel
import com.panevrn.domain.repository.CoursesRepository

class GetFavoritesCoursesUseCase(private val repository: CoursesRepository) {
    suspend operator fun invoke(): List<CourseModel> {
        return repository.getFavoritesCourses()
    }

}