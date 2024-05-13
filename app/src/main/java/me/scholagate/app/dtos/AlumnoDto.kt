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
    constructor(bytes: ByteArray) : this() {
        val json = String(bytes, Charsets.UTF_8)
        val gson = Gson()
        val alumno = gson.fromJson(json, AlumnoDto::class.java)
        id = alumno.id
        idGrupo = alumno.idGrupo
        nombre = alumno.nombre
        fechaNac = alumno.fechaNac
        foto = alumno.foto
    }

    fun isMayorDeEdad(): Boolean {
        val fechaNac = LocalDate.parse(fechaNac)
        return LocalDate.now().year - fechaNac.year >= 18
    }

    fun toBytes(): ByteArray {
        val gson = Gson()
        val json = gson.toJson(this)
        return json.toByteArray(Charsets.UTF_8)
    }
}