package me.scholagate.app.dtos

import java.time.Instant

/**
 * Clase que representa un reporte en la aplicación.
 *
 * @property id El id del reporte.
 * @property idAlumno El id del alumno al que pertenece el reporte.
 * @property idUsuario El id del usuario que realizó el reporte.
 * @property tipo El tipo de reporte.
 * @property motivo El motivo del reporte.
 * @property fecha La fecha en la que se realizó el reporte.
 */
data class ReporteDto(
    val id: Long = -1,
    val idAlumno: Int = -1,
    val idUsuario: Int = -1,
    var tipo: String = "",
    val motivo: String = "",
    val fecha: String = Instant.now().toString(),
)