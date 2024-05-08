package me.scholagate.app.dtos

data class UsuarioDto (
    val id: Int,
    val nombre: String,
    val correo: String,
    val rol: String
)