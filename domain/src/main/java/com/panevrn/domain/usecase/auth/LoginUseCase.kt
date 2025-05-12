package com.panevrn.domain.usecase.auth

import com.panevrn.domain.repository.EnterRepository

class LoginUseCase(private val repository: EnterRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repository.login(email, password)
    }
}