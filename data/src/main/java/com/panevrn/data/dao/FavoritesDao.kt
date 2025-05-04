package com.panevrn.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.panevrn.data.entity.CourseEntity


@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: CourseEntity)

    @Query("DELETE FROM favorites_courses WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM favorites_courses")
    suspend fun getAll(): List<CourseEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites_courses WHERE id = :id)")
    suspend fun isCourseLiked(id: String): Boolean
}