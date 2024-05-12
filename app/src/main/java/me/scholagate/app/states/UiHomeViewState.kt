package me.scholagate.app.states

import me.scholagate.app.dtos.UsuarioDto

data class UiHomeViewState(
    val homeState: HomeState,
    var usuarioDto: UsuarioDto,
)