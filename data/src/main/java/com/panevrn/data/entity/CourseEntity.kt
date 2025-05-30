package com.panevrn.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorites_courses")
data class CourseEntity(
    @PrimaryKey val id: String,
    val title: String,
    val text: String,
    val price: String,
    val rate: Double,
    val startDate: String,
    val publishDate: String,
)