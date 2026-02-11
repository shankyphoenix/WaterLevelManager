package link.phoenixwork.waterlevelmanager.data.ui

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoggedIn: Boolean = false,
    val error: String? = null,
    var isLoading: Boolean = false,
    val isNetworkAvailable: Boolean = false
)
