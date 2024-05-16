package me.scholagate.app.states

import me.scholagate.app.dtos.UsuarioDto

sealed class HomeState {
    data object None : HomeState()
    data object Loading : HomeState()
    data class Success(val usuario: UsuarioDto) : HomeState()
    data class Error(val message: String) : HomeState()
}