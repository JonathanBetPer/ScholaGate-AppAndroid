package me.scholagate.app.dtos

/**
 * Clase que representa un usuario en la aplicación.
 *
 * @property id El id del usuario.
 * @property nombre El nombre del usuario.
 * @property correo El correo del usuario.
 * @property rol El rol del usuario.
 */
data class UsuarioDto (
    val id: Int = -1,
    val nombre: String = "",
    val correo: String = "",
    val rol: String = "User"
)