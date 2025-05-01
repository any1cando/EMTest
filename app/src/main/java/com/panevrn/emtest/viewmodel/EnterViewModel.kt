package com.panevrn.emtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panevrn.domain.usecase.onboarding.CompleteOnboardingUseCase
import com.panevrn.domain.usecase.onboarding.IsOnboardingCompletedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterViewModel @Inject constructor(
    private val completeOnboardingUseCase: CompleteOnboardingUseCase,
    private val isOnboardingCompletedUseCase: IsOnboardingCompletedUseCase
): ViewModel() {

    fun completeOnboarding() {
        viewModelScope.launch {
            completeOnboardingUseCase()
        }
    }

    fun isOnboardingCompleted(): Boolean {
        var result: Boolean = false  // небольшая заглушка
        viewModelScope.launch {
            result = isOnboardingCompletedUseCase()
        }
        return result
    }

}