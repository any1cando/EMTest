package com.panevrn.emtest.viewmodel.states

sealed class LoadState {
    object Idle : LoadState()
    object Loading : LoadState()
    object Success : LoadState()
    data class Error(val message: String?) : LoadState()
}