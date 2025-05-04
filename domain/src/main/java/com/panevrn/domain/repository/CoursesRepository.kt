package com.panevrn.domain.repository

import com.panevrn.domain.model.CourseModel

interface CoursesRepository {
    suspend fun getCourses(): List<CourseModel>
}