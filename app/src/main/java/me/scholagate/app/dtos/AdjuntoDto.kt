package me.scholagate.app.dtos

data class AdjuntoDto(
    val id: Int = -1,
    val idReporte: Long = -1,
    val nombre: String = "",
    val foto: ByteArray = byteArrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AdjuntoDto

        return foto.contentEquals(other.foto)
    }

    override fun hashCode(): Int {
        return foto.contentHashCode()
    }
}