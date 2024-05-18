package me.scholagate.app.dtos

/**
 * Clase que representa las credenciales de un usuario en la aplicación.
 *
 * @property nombreUsuario El nombre de usuario.
 * @property password La contraseña del usuario.
 */
data class CredencialesDto(
    var nombreUsuario: String = "",
    var password: String = ""
)
