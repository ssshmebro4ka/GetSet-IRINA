package com.example.getset.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getset.data.model.UserProfile
import com.example.getset.data.repository.UserProfileRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: UserProfileRepository = UserProfileRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ProfileEffect>()
    val effect = _effect.asSharedFlow()

    init {
        handleIntent(ProfileIntent.LoadProfile)
    }

    fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadProfile -> loadProfile()
            is ProfileIntent.UpdateGender -> updateGender(intent.gender)
            is ProfileIntent.UpdateHeight -> updateHeight(intent.height)
            is ProfileIntent.UpdateMyWeight -> updateMyWeight(intent.weight)
            is ProfileIntent.UpdateWantWeight -> updateWantWeight(intent.weight)
            is ProfileIntent.SaveProfile -> saveProfile()
            is ProfileIntent.DismissError -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun loadProfile() {
        _state.update { it.copy(isLoading = true) }
        repository.loadProfile { profile, error ->
            if (profile != null) {
                _state.update {
                    it.copy(
                        gender = profile.gender,
                        height = profile.height,
                        myWeight = profile.myWeight,
                        wantWeight = profile.wantWeight,
                        isLoading = false,
                        isDataLoaded = true
                    )
                }
                validateForm()
            } else if (error != null) {
                _state.update { it.copy(errorMessage = error, isLoading = false) }
            } else {
                _state.update { it.copy(isLoading = false, isDataLoaded = true) }
            }
        }
    }

    private fun updateGender(gender: String) {
        _state.update { it.copy(gender = gender) }
        validateForm()
    }

    private fun updateHeight(height: String) {
        _state.update { it.copy(height = height) }
        validateForm()
    }

    private fun updateMyWeight(weight: String) {
        _state.update { it.copy(myWeight = weight) }
        validateForm()
    }

    private fun updateWantWeight(weight: String) {
        _state.update { it.copy(wantWeight = weight) }
        validateForm()
    }

    private fun validateForm() {
        _state.update {
            it.copy(
                isFormValid = it.gender.isNotBlank() &&
                        it.height.isNotBlank() &&
                        it.myWeight.isNotBlank() &&
                        it.wantWeight.isNotBlank()
            )
        }
    }

    private fun saveProfile() {
        val currentState = _state.value
        if (!currentState.isFormValid || currentState.isLoading) return

        _state.update { it.copy(isLoading = true, errorMessage = null, successMessage = null) }

        repository.loadProfile { profile, _ ->
            val existingProfile = profile ?: UserProfile()
            val updatedProfile = existingProfile.copy(
                gender = currentState.gender,
                height = currentState.height,
                myWeight = currentState.myWeight,
                wantWeight = currentState.wantWeight
            )

            repository.saveProfile(updatedProfile) { success, error ->
                if (success) {
                    _state.update { it.copy(isLoading = false, successMessage = "Данные сохранены!") }
                    viewModelScope.launch {
                        _effect.emit(ProfileEffect.NavigateBack)
                    }
                } else {
                    _state.update { it.copy(isLoading = false, errorMessage = error ?: "Ошибка сохранения") }
                }
            }
        }
    }
}