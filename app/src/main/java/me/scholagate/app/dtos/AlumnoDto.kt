package me.scholagate.app.dtos

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Clase que representa un alumno en la aplicación.
 *
 * @property id El id del alumno.
 * @property idGrupo El id del grupo al que pertenece el alumno.
 * @property nombre El nombre del alumno.
 * @property fechaNac La fecha de nacimiento del alumno.
 * @property foto La foto del alumno.
 */
data class AlumnoDto(
    var id: Int = -1,
    var idGrupo: Int = -1,
    var nombre: String = "",
    var fechaNac: String = "",
    var foto: String = ""
){
    fun isMayorDeEdad(): Boolean {
        val fechaNac = LocalDate.parse(fechaNac)
        return LocalDate.now().year - fechaNac.year >= 18
    }

    fun fechaToString(): String {
        return LocalDate.parse(this.fechaNac).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }
}