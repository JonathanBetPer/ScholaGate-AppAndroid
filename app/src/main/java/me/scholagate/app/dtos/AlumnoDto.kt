package me.scholagate.app.dtos

data class AlumnoDto(
    val id: Int,
    val idGrupo: Int,
    val nombre: String,
    val fechaNac: String,
    val foto: ByteArray
)