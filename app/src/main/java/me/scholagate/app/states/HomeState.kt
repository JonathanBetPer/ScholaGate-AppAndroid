package me.scholagate.app.states

sealed class HomeState {
    data object None : HomeState()
    data object Loading : HomeState()
    data class Success(val token: String) : HomeState()
    data class Error(val message: String) : HomeState()
}