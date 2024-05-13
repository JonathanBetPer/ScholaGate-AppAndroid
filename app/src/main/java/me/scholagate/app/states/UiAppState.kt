package me.scholagate.app.states

import me.scholagate.app.dtos.CredencialesDto
data class UiAppState(
    var appState: AppState,
    var credencialesDto: CredencialesDto?,
)