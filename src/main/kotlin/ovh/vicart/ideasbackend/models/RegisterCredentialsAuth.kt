package ovh.vicart.ideasbackend.models

data class RegisterCredentialsAuth(val username: String, val password: String, val email: String,
    val displayName: String)