package me.scholagate.app.dtos

import java.time.Instant


data class ReporteDto(
    val id: Long = -1,
    val idAlumno: Int = -1,
    val idUsuario: Int = -1,
    var tipo: String = "",
    val motivo: String = "",
    val fecha: String = Instant.now().toString(),
){
    fun setEntrada() {
        this.tipo = "Entrada"
    }
    fun setSalida() {
        this.tipo = "Salida"
    }
}