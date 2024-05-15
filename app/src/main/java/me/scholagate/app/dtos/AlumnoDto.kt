package me.scholagate.app.dtos

import com.google.gson.Gson
import java.time.LocalDate

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

}