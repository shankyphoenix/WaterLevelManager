package link.phoenixwork.waterlevelmanager.events

sealed class LoginUiEvent {
    data class EmailChanged(val value: String) : LoginUiEvent()
    data class PasswordChanged(val value: String) : LoginUiEvent()
    object LoginClicked : LoginUiEvent()
}