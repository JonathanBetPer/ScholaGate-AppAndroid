package me.scholagate.app.dtos

import java.util.Date

data class ReporteDto(
    val id: Long,
    val idAlumno: Int,
    val idUsuario: Int,
    val tipo: String,
    val motivo: String,
    val fecha: Date
)