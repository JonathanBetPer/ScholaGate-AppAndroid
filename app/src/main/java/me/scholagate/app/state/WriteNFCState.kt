package me.scholagate.app.state

import me.scholagate.app.dtos.AlumnoDto

data class WriteNFCState(
    val listaAlumnos: List<AlumnoDto>,
    val alumnoSeleccionado: AlumnoDto
)
