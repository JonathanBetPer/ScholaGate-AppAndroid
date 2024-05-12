package me.scholagate.app.states

sealed class AppState {
    data object Loading : AppState()
    data class Success(val credencielesExito: Boolean) : AppState()
}