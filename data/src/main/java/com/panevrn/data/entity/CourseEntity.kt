package com.panevrn.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


// TODO: Доделать работу в локальной БД, чтобы можно было доставать лайкнутые курсы
@Entity(tableName = "favorite_courses")
data class CourseEntity(
    @PrimaryKey val id: String,
    val title: String,
    val text: String,
    val price: String,
    val rate: Double,
    val startDate: String,
    val publishDate: String,
    val imageUrl: String,
    val hasLike: Boolean = true  // всегда true, потому что это таблица "избранное"
)