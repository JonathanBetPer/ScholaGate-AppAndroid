package me.scholagate.app.states

data class UiLoginViewState(
    val loginState: LoginState,
    var token: String,
)