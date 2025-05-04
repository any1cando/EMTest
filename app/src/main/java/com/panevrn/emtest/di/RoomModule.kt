package com.panevrn.emtest.di

import android.content.Context
import androidx.room.Room
import com.panevrn.data.dao.AppDatabase
import com.panevrn.data.dao.FavoritesDao
import com.panevrn.domain.repository.CoursesRepository
import com.panevrn.domain.usecase.main.common.ToggleLikeCourseUseCase
import com.panevrn.domain.usecase.main.favoritescourses.GetFavoritesCoursesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    // Репозиторий мы провели еще в модуле NetworkModule. Точно не лучшая практика.
    @Provides
    fun provideGetFavoritesCoursesUseCase(repository: CoursesRepository): GetFavoritesCoursesUseCase {
        return GetFavoritesCoursesUseCase(repository)
    }

    @Provides
    fun provideToggleLikeCourseUseCase(repository: CoursesRepository): ToggleLikeCourseUseCase {
        return ToggleLikeCourseUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideFavoritesDao(appDatabase: AppDatabase): FavoritesDao {
        return appDatabase.favoritesDao()
    }


}