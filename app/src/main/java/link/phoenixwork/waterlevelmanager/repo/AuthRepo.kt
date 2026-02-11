package link.phoenixwork.waterlevelmanager.repo

import link.phoenixwork.waterlevelmanager.data.remote.LoginRequest
import link.phoenixwork.waterlevelmanager.data.remote.LoginResponse
import link.phoenixwork.waterlevelmanager.domain.remote.MainApiInterface
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val api: MainApiInterface
) {
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return runCatching {

            val response = api.login(LoginRequest(email, password))

            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty body")
            } else {
                throw Exception(response.errorBody()?.string() ?: "Login failed")
            }
        }
    }
}