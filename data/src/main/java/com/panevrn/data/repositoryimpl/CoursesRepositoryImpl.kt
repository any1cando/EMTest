package com.panevrn.data.repositoryimpl


import com.google.gson.Gson
import com.panevrn.data.entity.CourseListResponse
import com.panevrn.data.mapper.toDomain
import com.panevrn.data.network.ApiService
import com.panevrn.domain.model.CourseModel
import com.panevrn.domain.repository.CoursesRepository
import javax.inject.Inject


class CoursesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson
): CoursesRepository {
    override suspend fun getCourses(): List<CourseModel> {
        val response = apiService.getCourses()
        val jsonString = response.string()
        val coursesResponse = gson.fromJson(jsonString, CourseListResponse::class.java)
        return coursesResponse.courses.map { it.toDomain() }
    }

}