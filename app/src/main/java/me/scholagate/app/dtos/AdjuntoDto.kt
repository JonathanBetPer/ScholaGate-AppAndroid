package me.scholagate.app.dtos

/**
 * Clase que representa un adjunto en la aplicación.
 *
 * @property id El id del adjunto.
 * @property idReporte El id del reporte al que pertenece el adjunto.
 * @property nombre El nombre del adjunto.
 * @property foto La foto del adjunto.
 */
data class AdjuntoDto(
    val id: Int = -1,
    val idReporte: Long = -1,
    val nombre: String = "",
    val foto: String = ""
)