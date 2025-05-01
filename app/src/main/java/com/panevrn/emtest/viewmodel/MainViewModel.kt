package com.panevrn.emtest.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panevrn.domain.usecase.onboarding.IsOnboardingCompletedUseCase
import com.panevrn.emtest.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Оказалось не нужно
@HiltViewModel
class MainViewModel @Inject constructor(
    private val isOnboardingCompletedUseCase: IsOnboardingCompletedUseCase
): ViewModel() {

    private val _startDestination = MutableLiveData<Int>()
    val startDestination: LiveData<Int> = _startDestination

    init {
        viewModelScope.launch {
            val completed: Boolean = isOnboardingCompletedUseCase()
            val destination = if (completed) R.id.authFragment else R.id.onboardingFragment
            _startDestination.postValue(destination)
        }
    }

}