package com.panevrn.domain.usecase.onboarding

import com.panevrn.domain.repository.EnterRepository

class CompleteOnboardingUseCase(
    private val repository: EnterRepository
) {
    suspend operator fun invoke() = repository.completeOnboarding()

}