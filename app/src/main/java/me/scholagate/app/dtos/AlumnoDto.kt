package me.scholagate.app.dtos

import java.time.LocalDate

data class AlumnoDto(
    val id: Int = -1,
    val idGrupo: Int = -1,
    val nombre: String = "",
    val fechaNac: String = "",
    val foto: String = ""
){
    fun isMayorDeEdad(): Boolean {
        val fechaNac = LocalDate.parse(fechaNac)
        return LocalDate.now().year - fechaNac.year >= 18
    }
}