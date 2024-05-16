package me.scholagate.app.states;

import me.scholagate.app.dtos.AlumnoDto

sealed class ValidationState {
    data object Loading : ValidationState()
    data class Success(val alumno: AlumnoDto) : ValidationState()
}