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

class RegisterUseCaseTest {

    private lateinit var repository: EnterRepository
    private lateinit var useCase: RegisterUseCase

    @Before
    fun setUp() {
        repository = mock(EnterRepository::class.java)
        useCase = RegisterUseCase(repository)
    }

    @Test
    fun `register returns success when repository succeeds`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password123"
        `when`(repository.register(email, password)).thenReturn(Result.success(Unit))

        // Act
        val result = useCase(email, password)

        // Assert
        assertTrue(result.isSuccess)
        verify(repository).register(email, password)
    }

    @Test
    fun `register returns failure when repository fails`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "123"
        val exception = RuntimeException("Registration error")
        `when`(repository.register(email, password)).thenReturn(Result.failure(exception))

        // Act
        val result = useCase(email, password)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verify(repository).register(email, password)
    }
}