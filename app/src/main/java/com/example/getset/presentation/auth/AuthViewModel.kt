package com.example.getset.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AuthEffect>()
    val effect = _effect.asSharedFlow()

    private val auth = FirebaseAuth.getInstance()

    fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.UpdateLogin -> updateLogin(intent.login)
            is AuthIntent.UpdatePassword -> updatePassword(intent.password)
            is AuthIntent.SignIn -> signIn()
            is AuthIntent.SignUp -> signUp()
            is AuthIntent.DismissError -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun updateLogin(login: String) {
        _state.update { 
            it.copy(
                login = login, 
                errorMessage = null,
                isFormValid = login.isNotBlank() && it.password.isNotBlank()
            ) 
        }
    }

    private fun updatePassword(password: String) {
        _state.update { 
            it.copy(
                password = password, 
                errorMessage = null,
                isFormValid = it.login.isNotBlank() && password.isNotBlank()
            ) 
        }
    }

    private fun signIn() {
        val currentState = _state.value
        if (!currentState.isFormValid || currentState.isLoading) return

        _state.update { it.copy(isLoading = true, errorMessage = null) }

        auth.signInWithEmailAndPassword(currentState.login, currentState.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _state.update { it.copy(isLoading = false) }
                    viewModelScope.launch {
                        _effect.emit(AuthEffect.NavigateToHome)
                    }
                } else {
                    val error = task.exception?.localizedMessage ?: "Ошибка входа"
                    _state.update { it.copy(isLoading = false, errorMessage = error) }
                }
            }
    }

    private fun signUp() {
        val currentState = _state.value
        if (!currentState.isFormValid || currentState.isLoading) return

        _state.update { it.copy(isLoading = true, errorMessage = null) }

        auth.createUserWithEmailAndPassword(currentState.login, currentState.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _state.update { it.copy(isLoading = false) }
                    viewModelScope.launch {
                        _effect.emit(AuthEffect.NavigateToPurpose)
                    }
                } else {
                    val error = task.exception?.localizedMessage ?: "Ошибка регистрации"
                    _state.update { it.copy(isLoading = false, errorMessage = error) }
                }
            }
    }
}