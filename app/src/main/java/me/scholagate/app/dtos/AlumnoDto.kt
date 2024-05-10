package me.scholagate.app.dtos

import java.time.LocalDate

data class AlumnoDto(
    val id: Int = -1,
    val idGrupo: Int = -1,
    val nombre: String = "",
    val fechaNac: LocalDate = LocalDate.now(),
    val foto: ByteArray = byteArrayOf()
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlumnoDto

        return foto.contentEquals(other.foto)
    }

    override fun hashCode(): Int {
        return foto.contentHashCode()
    }
}