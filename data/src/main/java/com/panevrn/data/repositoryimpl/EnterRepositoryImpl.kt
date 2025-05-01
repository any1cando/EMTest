package com.panevrn.data.repositoryimpl

import android.content.SharedPreferences
import com.panevrn.domain.repository.EnterRepository
import javax.inject.Inject

class EnterRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : EnterRepository {

    companion object {
        private const val ONBOARDING_COMPLETED_KEY = "onboarding_completed"
    }

    override suspend fun completeOnboarding() {
        sharedPreferences.edit().putBoolean(ONBOARDING_COMPLETED_KEY, true).apply()
    }

    override fun isOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean(ONBOARDING_COMPLETED_KEY, false)
    }
}