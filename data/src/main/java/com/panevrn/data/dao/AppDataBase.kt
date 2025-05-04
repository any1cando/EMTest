package com.panevrn.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.panevrn.data.entity.CourseEntity

@Database(entities = [CourseEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}