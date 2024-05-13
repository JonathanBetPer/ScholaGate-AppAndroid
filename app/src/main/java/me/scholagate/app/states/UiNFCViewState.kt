package me.scholagate.app.states

import me.scholagate.app.dtos.AlumnoDto

data class UiNFCViewState(
    var NFCState: NFCState,
    var alumno: AlumnoDto,
)