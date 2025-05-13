package com.panevrn.emtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panevrn.domain.usecase.auth.LoginUseCase
import com.panevrn.domain.usecase.onboarding.CompleteOnboardingUseCase
import com.panevrn.domain.usecase.onboarding.IsOnboardingCompletedUseCase
import com.panevrn.emtest.viewmodel.states.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val completeOnboardingUseCase: CompleteOnboardingUseCase,
    private val isOnboardingCompletedUseCase: IsOnboardingCompletedUseCase,
    private val loginUseCase: LoginUseCase
): ViewModel() {

    // Поля LiveData для email и password
    private val _email = MutableLiveData("")
    private val _password = MutableLiveData("")

    // Поля LiveData для кнопки "Продолжить"
    private val _isLoginEnabled = MutableLiveData(false)
    val isLoginEnabled: LiveData<Boolean> = _isLoginEnabled

    // Поля LiveData для результата авторизации
    private val _authState = MutableLiveData<LoadState>(LoadState.Idle)  // изначальное состояние - ничего не происходит
    val authState: LiveData<LoadState> = _authState


    // Метод, выполняющийся при нажатии на кнопку "Продолжить" онбординга

    fun completeOnboarding() {
        viewModelScope.launch {
            completeOnboardingUseCase()
        }
    }


    // Метод, выполняющийся при нажатии на кнопку "Войти" в AuthFragment
    fun login() {
        val email = _email.value ?: return
        val password = _password.value ?: return
        _authState.value = LoadState.Loading
        viewModelScope.launch {
            val result = loginUseCase(email, password)
            _authState.value = if (result.isSuccess) {
                LoadState.Success
            } else {
                LoadState.Error(result.exceptionOrNull()?.message)
            }
        }
    }


    // Метод, выполняющийся в методе OnViewCreated() в OnboardingFragment

    fun isOnboardingCompleted(): Boolean {
        var result: Boolean = false  // небольшая заглушка
        viewModelScope.launch {
            result = isOnboardingCompletedUseCase()
        }
        return result
    }


    // Метод, который срабатывает при изменении EditText "email" в AuthFragment

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        validate()
    }


    // Метод, который срабатывает при изменении EditText "password" в AuthFragment

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
        validate()
    }


    // Метод, выполняющий проверку на корректность введенных данных
    private fun validate() {
        _isLoginEnabled.value = isEmailValid(_email.value!!) && _password.value!!.isNotEmpty()
    }


    // Метод, выполняющий проверку "email" на корректность
    private fun isEmailValid(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return regex.matches(email)
    }

    // Сброс состояния авторизации
    fun resetAuthState() {
        _authState.value = LoadState.Idle
    }


}