package com.panevrn.emtest.di

import com.google.firebase.auth.FirebaseAuth
import com.panevrn.domain.repository.EnterRepository
import com.panevrn.domain.usecase.auth.LoginUseCase
import com.panevrn.domain.usecase.onboarding.IsOnboardingCompletedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideLoginUseCase(repository: EnterRepository) =
        LoginUseCase(repository)

}