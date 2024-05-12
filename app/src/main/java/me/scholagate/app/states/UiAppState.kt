package me.scholagate.app.states

import me.scholagate.app.dtos.Credenciales
data class UiAppState(
    var appState: AppState,
    var credenciales: Credenciales?,
)