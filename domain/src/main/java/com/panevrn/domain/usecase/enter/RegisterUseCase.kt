package com.panevrn.domain.usecase.enter

import com.panevrn.domain.repository.EnterRepository

class RegisterUseCase(private val repository: EnterRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repository.register(email, password)

    }
}