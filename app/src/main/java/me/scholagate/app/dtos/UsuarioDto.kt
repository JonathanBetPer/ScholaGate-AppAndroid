package me.scholagate.app.dtos

data class UsuarioDto (
    val id: Int = -1,
    val nombre: String = "",
    val correo: String = "",
    val rol: String = "User"
)