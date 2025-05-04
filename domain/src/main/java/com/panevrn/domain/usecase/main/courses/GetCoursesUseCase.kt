package com.panevrn.domain.usecase.main.courses

import com.panevrn.domain.model.CourseModel
import com.panevrn.domain.repository.CoursesRepository

class GetCoursesUseCase(private val repository: CoursesRepository) {
    suspend operator fun invoke(): List<CourseModel> {
        return repository.getCourses()
    }
}