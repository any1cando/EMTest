package com.panevrn.emtest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panevrn.domain.model.CourseModel
import com.panevrn.domain.usecase.main.common.ToggleLikeCourseUseCase
import com.panevrn.domain.usecase.main.courses.GetCoursesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val toggleLikeCourseUseCase: ToggleLikeCourseUseCase
): ViewModel() {

    private val _courses = MutableLiveData<List<CourseModel>>()
    val courses: LiveData<List<CourseModel>> get() = _courses
    private var isSorted = true  // Флаг в ViewModel для отображение отсортированных курсов

    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            try {
                val courses = getCoursesUseCase()
                _courses.postValue(courses)
            } catch (e: Exception) {
                Log.e("CoursesViewModel", "Ошибка загрузки курсов: ${e.message}")
                loadTestCourses()
            }
        }
    }

    // Обновляем локально
    fun toggleLike(course: CourseModel) {
        viewModelScope.launch {
            toggleLikeCourseUseCase(course)
            _courses.value = _courses.value?.map {
                if (it.id == course.id) it.copy(hasLike = !it.hasLike) else it
            }
        }
    }

    fun sortCoursesByPublishDate() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val sortedCourses = _courses.value?.sortedWith(compareBy { LocalDate.parse(it.publishDate, formatter) })

        _courses.value = if(isSorted) sortedCourses else sortedCourses?.reversed()
        isSorted = !isSorted
    }

    fun selectCourse(course: CourseModel) {
        // TODO: Можно сделать навигацию к подробному экрану курса
    }

    // Метод заглушка для хардкода, если что-то не так будет с ответом от сервера
    private fun loadTestCourses() {
        val testCourses = listOf(
            CourseModel(
                id = "100",
                title = "Java-разработчик с нуля",
                text = "Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.",
                price = "999",
                rate = 4.9,
                startDate = "2024-05-22",
                publishDate = "2024-02-02",
                hasLike = false
            ),
            CourseModel(
                id = "101",
                title = "3D-дженералист",
                text = "Освой профессию 3D-дженералиста и стань универсальным специалистом, который умеет создавать 3D-модели, текстуры и анимации, а также может строить карьеру в геймдеве, кино, рекламе или дизайне.",
                price = "12 000",
                rate = 3.9,
                startDate = "2024-09-10",
                publishDate = "2024-01-20",
                hasLike = false
            ),
            CourseModel(
                id = "102",
                title = "Python Advanced. Для продвинутых",
                text = "Вы узнаете, как разрабатывать гибкие и высокопроизводительные серверные приложения на языке Kotlin. Преподаватели на вебинарах покажут пример того, как разрабатывается проект маркетплейса: от идеи и постановки задачи – до конечного решения",
                price = "1 299",
                rate = 4.3,
                startDate = "2024-10-12",
                publishDate = "2024-08-10",
                hasLike = true
            ),
            CourseModel(
                id = "103",
                title = "Системный аналитик",
                text = "Освоите навыки системной аналитики с нуля за 9 месяцев. Будет очень много практики на реальных проектах, чтобы вы могли сразу стартовать в IT.",
                price = "1 199",
                rate = 4.5,
                startDate = "2024-04-15",
                publishDate = "2024-01-13",
                hasLike = false
            ),
            CourseModel(
                id = "104",
                title = "Аналитик данных",
                text = "В этом уроке вы узнаете, кто такой аналитик данных и какие задачи он решает. А главное — мы расскажем, чему вы научитесь по завершении программы обучения профессии «Аналитик данных».",
                price = "899",
                rate = 4.7,
                startDate = "2024-06-20",
                publishDate = "2024-03-12",
                hasLike = false
            )
        )
        _courses.postValue(testCourses)
    }
}