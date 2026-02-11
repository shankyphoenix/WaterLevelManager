package link.phoenixwork.waterlevelmanager.data.remote

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean,
    val token: String?,
    val message: String?
)