package com.panevrn.data.entity

import com.google.gson.annotations.SerializedName


data class CourseListResponse(
    @SerializedName("courses")
    val courses: List<CourseDto>
)
