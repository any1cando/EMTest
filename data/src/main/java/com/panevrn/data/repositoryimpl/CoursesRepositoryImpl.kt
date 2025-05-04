package com.panevrn.data.repositoryimpl


import com.google.gson.Gson
import com.panevrn.data.dao.FavoritesDao
import com.panevrn.data.entity.CourseListResponse
import com.panevrn.data.mapper.toDomain
import com.panevrn.data.mapper.toEntity
import com.panevrn.data.network.ApiService
import com.panevrn.domain.model.CourseModel
import com.panevrn.domain.repository.CoursesRepository
import javax.inject.Inject


class CoursesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val favoritesDao: FavoritesDao
): CoursesRepository {
    override suspend fun getCourses(): List<CourseModel> {
        val response = apiService.getCourses()
        val jsonString = response.string()
        val coursesResponse = gson.fromJson(jsonString, CourseListResponse::class.java)
        return coursesResponse.courses.map { it.toDomain() }
    }

    override suspend fun getFavoritesCourses(): List<CourseModel> {
        return favoritesDao.getAll().map { it.toDomain() }
    }

    override suspend fun isCourseLiked(id: String): Boolean {
        return favoritesDao.isCourseLiked(id)
    }

    override suspend fun addCourseToFavorites(course: CourseModel) {
        favoritesDao.insert(course.toEntity())
    }

    override suspend fun removeCourseFromFavorites(id: String) {
        favoritesDao.deleteById(id)
    }

}