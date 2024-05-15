package me.scholagate.app.states

import me.scholagate.app.dtos.AlumnoDto

sealed class NFCState {
    data object None : NFCState()
    data object Loading : NFCState()
    data object ReadyToRead : NFCState()
    data class ReadyToWrite(val alumno: AlumnoDto) : NFCState()
    data class SuccessRead(val idAlumno: Int) : NFCState()
    data class SuccessWrite(val alumno: AlumnoDto) : NFCState()
    data class Error(val message: String) : NFCState()
}