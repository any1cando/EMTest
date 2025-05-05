package com.panevrn.emtest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panevrn.domain.model.CourseModel
import com.panevrn.domain.usecase.main.common.ToggleLikeCourseUseCase
import com.panevrn.domain.usecase.main.favoritescourses.GetFavoritesCoursesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesCoursesViewModel @Inject constructor(
    private val getFavoritesCoursesUseCase: GetFavoritesCoursesUseCase,
    private val toggleLikeCourseUseCase: ToggleLikeCourseUseCase
): ViewModel() {

    private val _favoritesCourses = MutableLiveData<List<CourseModel>>()
    val favoritesCourses: LiveData<List<CourseModel>> get() = _favoritesCourses

    init {
        loadFavoritesCourses()
    }

    fun loadFavoritesCourses() {
        viewModelScope.launch {
            try {
                val favoritesCoursesList = getFavoritesCoursesUseCase()
                _favoritesCourses.postValue(favoritesCoursesList)
            } catch (e: Exception) {
                Log.e("Room Exception: ", e.message.toString())
            }
        }
    }

    fun toggleLike(course: CourseModel) {
        viewModelScope.launch {
            toggleLikeCourseUseCase(course)
            loadFavoritesCourses()
            _favoritesCourses.value = _favoritesCourses.value?.map {
                if (it.id == course.id) it.copy(hasLike = !it.hasLike) else it
            }
        }
    }


    fun selectCourse(course: CourseModel) {

    }

}