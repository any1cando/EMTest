package com.panevrn.emtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panevrn.domain.model.CourseModel
import com.panevrn.domain.usecase.main.favoritescourses.GetFavoritesCoursesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesCoursesViewModel @Inject constructor(
    private val getFavoritesCoursesUseCase: GetFavoritesCoursesUseCase
): ViewModel() {

    private val _favoritesCourses = MutableLiveData<List<CourseModel>>()
    val favoritesCourses: LiveData<List<CourseModel>> get() = _favoritesCourses

    init {
        loadFavoritesCourses()
    }

    private fun loadFavoritesCourses() {
        viewModelScope.launch {
            try {
                val favoritesCourses = getFavoritesCoursesUseCase()
                _favoritesCourses.postValue(favoritesCourses)
            } catch (e: Exception) {
                // TODO: Сделать обработку ошибки, если ничего не пришло из Room
            }
        }
    }

}