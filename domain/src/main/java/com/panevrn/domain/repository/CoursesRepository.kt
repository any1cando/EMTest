package com.panevrn.domain.repository

import com.panevrn.domain.model.CourseModel

interface CoursesRepository {
    suspend fun getCourses(): List<CourseModel>
    suspend fun getFavoritesCourses(): List<CourseModel>

    suspend fun isCourseLiked(id: String): Boolean
    suspend fun addCourseToFavorites(course: CourseModel)
    suspend fun removeCourseFromFavorites(id: String)
}