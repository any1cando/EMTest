package com.panevrn.domain.repository

interface EnterRepository {
    suspend fun completeOnboarding()
    fun isOnboardingCompleted(): Boolean
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String): Result<Unit>
}