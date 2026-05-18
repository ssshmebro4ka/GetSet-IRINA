package com.example.getset.presentation.auth

data class AuthState(
    val login: String = "",
    val password: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isFormValid: Boolean = false
)

sealed class AuthIntent {
    data class UpdateLogin(val login: String) : AuthIntent()
    data class UpdatePassword(val password: String) : AuthIntent()
    object SignIn : AuthIntent()
    object SignUp : AuthIntent()
    object DismissError : AuthIntent()
}

sealed class AuthEffect {
    object NavigateToHome : AuthEffect()
    object NavigateToPurpose : AuthEffect()
    data class ShowError(val message: String) : AuthEffect()
}