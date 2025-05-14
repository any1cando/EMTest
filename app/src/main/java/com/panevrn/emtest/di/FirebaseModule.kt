package com.panevrn.emtest.di

import com.google.firebase.auth.FirebaseAuth
import com.panevrn.domain.repository.EnterRepository
import com.panevrn.domain.usecase.enter.LoginUseCase
import com.panevrn.domain.usecase.enter.RegisterUseCase
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

    @Provides
    fun provideRegisterUseCase(repository: EnterRepository) =
        RegisterUseCase(repository)
}