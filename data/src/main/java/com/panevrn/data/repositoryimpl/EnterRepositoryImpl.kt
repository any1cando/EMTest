package com.panevrn.data.repositoryimpl

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.panevrn.domain.repository.EnterRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EnterRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val firebaseAuth: FirebaseAuth
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

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}