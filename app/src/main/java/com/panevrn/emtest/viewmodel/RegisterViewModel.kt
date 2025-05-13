package com.panevrn.emtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.panevrn.emtest.viewmodel.states.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



class RegisterViewModel(
    // TODO: RegisterUseCase
): ViewModel() {

    // Поля LiveData для email и password
    private val _email = MutableLiveData("")
    private val _password = MutableLiveData("")
    private val _passwordRepeat = MutableLiveData("")
    private val _isRegisterEnabled = MutableLiveData(false)
    val isRegisterEnabled: LiveData<Boolean> = _isRegisterEnabled
    private val _registerState = MutableLiveData<LoadState>(LoadState.Idle)
    val registerState: LiveData<LoadState> = _registerState

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