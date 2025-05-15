package com.panevrn.domain.usecase.enter

import com.panevrn.domain.repository.EnterRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import kotlin.test.Test
import kotlin.test.assertEquals

class LoginUseCaseTest {
    // Мокаем интерфейс репозитория и пишем сам use case
    private lateinit var repository: EnterRepository
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        // Создали мок-объект и передали его в use case
        repository = mock(EnterRepository::class.java)
        loginUseCase = LoginUseCase(repository)
    }

    @Test
    fun `login returns success when repository succeeds`() = runTest {
        // Arrange — входные данные
        val email = "test@example.com"
        val password = "password123"

        // Задаём поведение мока: возвращать успех
        `when`(repository.login(email, password)).thenReturn(Result.success(Unit))

        // Act — вызываем use case
        val result = loginUseCase(email, password)

        // Assert — проверяем, что вернулся успех
        assertTrue(result.isSuccess)
        verify(repository).login(email, password) // проверяем, что метод был вызван
    }

    @Test
    fun `login returns failure when repository fails`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "wrongPassword"
        val exception = RuntimeException("Invalid credentials")

        `when`(repository.login(email, password)).thenReturn(Result.failure(exception))

        // Act
        val result = loginUseCase(email, password)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verify(repository).login(email, password)
    }

}