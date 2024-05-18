package me.scholagate.app.dtos

import com.google.gson.Gson
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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