package com.panevrn.domain.repository

interface EnterRepository {
    suspend fun completeOnboarding()
    fun isOnboardingCompleted(): Boolean
}