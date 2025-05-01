package com.panevrn.domain.usecase.onboarding

import com.panevrn.domain.repository.EnterRepository

class IsOnboardingCompletedUseCase(private val repository: EnterRepository) {
    operator fun invoke() = repository.isOnboardingCompleted()
}