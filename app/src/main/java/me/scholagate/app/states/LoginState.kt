package me.scholagate.app.states

sealed class LoginState {
    data object None : LoginState()
    data object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}