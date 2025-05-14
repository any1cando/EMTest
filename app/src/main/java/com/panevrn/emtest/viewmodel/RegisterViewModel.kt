package com.panevrn.emtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panevrn.domain.usecase.enter.RegisterUseCase
import com.panevrn.emtest.viewmodel.states.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    // Поля LiveData для email, password и passwordRepeat
    private val _email = MutableLiveData("")
    private val _password = MutableLiveData("")
    private val _passwordRepeat = MutableLiveData("")
    // Переменная - валидация для полей. Можно ли нажимать на кнопку регистрации или нет
    private val _isRegisterEnabled = MutableLiveData(false)
    val isRegisterEnabled: LiveData<Boolean> = _isRegisterEnabled
    // Состояние для регистрации
    private val _registerState = MutableLiveData<LoadState>(LoadState.Idle)
    val registerState: LiveData<LoadState> = _registerState
    // Поля для отображения валидации и подсказок, как нужно вводить текст
    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError
    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError
    private val _passwordRepeatError = MutableLiveData<String?>()
    val passwordRepeatError: LiveData<String?> = _passwordRepeatError


    fun register() {
        val email = _email.value.orEmpty()
        val password = _password.value.orEmpty()

        _registerState.value = LoadState.Loading

        viewModelScope.launch {
            val result = registerUseCase(email, password)
            _registerState.value = if (result.isSuccess) {
                LoadState.Success
            } else {
                LoadState.Error(result.exceptionOrNull()?.message)
            }
        }
    }


    fun onEmailChanged(email: String) {
        _email.value = email
        validate()
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
        validate()
    }

    fun onRepeatPasswordChanged(repeatPassword: String) {
        _passwordRepeat.value = repeatPassword
        validate()
    }

    private fun validate() {
        val emailValid = isEmailValid(_email.value.orEmpty())
        val passwordValid = isPasswordValid(_password.value.orEmpty())
        val passwordsMatch = _password.value == _passwordRepeat.value

        _isRegisterEnabled.value = emailValid && passwordValid && passwordsMatch
    }

    private fun isEmailValid(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return regex.matches(email)
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }

    // Сброс состояния регистрации
    fun resetRegisterState() {
        _registerState.value = LoadState.Idle
    }

}