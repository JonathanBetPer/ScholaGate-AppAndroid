package me.scholagate.app.dtos

data class AdjuntoDto(
    val id: Int,
    val idReporte: Long,
    val nombre: String,
    val foto: ByteArray
)