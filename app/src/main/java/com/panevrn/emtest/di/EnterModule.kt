package com.panevrn.emtest.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.panevrn.data.repositoryimpl.EnterRepositoryImpl
import com.panevrn.domain.repository.EnterRepository
import com.panevrn.domain.usecase.onboarding.CompleteOnboardingUseCase
import com.panevrn.domain.usecase.onboarding.IsOnboardingCompletedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object EnterModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideEnterRepository(sharedPreferences: SharedPreferences, firebaseAuth: FirebaseAuth): EnterRepository {
        return EnterRepositoryImpl(sharedPreferences, firebaseAuth)
    }

    @Provides
    fun provideCompleteOnboardingUseCase(repository: EnterRepository) =
        CompleteOnboardingUseCase(repository)

    @Provides
    fun provideIsOnboardingCompletedUseCase(repository: EnterRepository) =
        IsOnboardingCompletedUseCase(repository)
}