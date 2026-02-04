package link.phoenixwork.waterlevelmanager.data.ui

data class Login(
    val email: String = "",
    val password: String = "",
    val isLoggedIn: Boolean = false,
    val error: String = "",
    var isLoading: Boolean = false,
    val isNetworkAvailable: Boolean = false
)
