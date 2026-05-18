package com.example.getset.presentation.purpose

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

class PurposeViewModel(
    private val repository: UserProfileRepository = UserProfileRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(PurposeState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PurposeEffect>()
    val effect = _effect.asSharedFlow()

    val allPurposes = listOf(
        "Стать сильнее",
        "Улучшить здоровье",
        "Сбросить вес",
        "Стать стройным и рельефным",
        "Набрать мышечную массу"
    )

    init {
        handleIntent(PurposeIntent.LoadPurposes)
    }

    fun handleIntent(intent: PurposeIntent) {
        when (intent) {
            is PurposeIntent.LoadPurposes -> loadPurposes()
            is PurposeIntent.TogglePurpose -> togglePurpose(intent.purpose)
            is PurposeIntent.SavePurposes -> savePurposes(false)
            is PurposeIntent.ChangePurposes -> savePurposes(true)
            is PurposeIntent.DismissError -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun loadPurposes() {
        _state.update { it.copy(isLoading = true, isDataLoaded = false) }
        repository.loadProfile { profile, error ->
            if (profile != null) {
                _state.update { 
                    it.copy(
                        selectedPurposes = profile.purposes.toSet(),
                        isLoading = false,
                        isDataLoaded = true,
                        isReadyToNext = profile.purposes.isNotEmpty()
                    ) 
                }
            } else {
                _state.update { it.copy(isLoading = false, isDataLoaded = true, errorMessage = error) }
            }
        }
    }

    private fun togglePurpose(purpose: String) {
        _state.update { currentState ->
            val newSelected = if (currentState.selectedPurposes.contains(purpose)) {
                currentState.selectedPurposes - purpose
            } else {
                currentState.selectedPurposes + purpose
            }
            currentState.copy(
                selectedPurposes = newSelected,
                isReadyToNext = newSelected.isNotEmpty()
            )
        }
    }

    private fun savePurposes(isChange: Boolean) {
        val currentState = _state.value
        if (!currentState.isReadyToNext || currentState.isLoading) return

        _state.update { it.copy(isLoading = true, errorMessage = null, successMessage = null) }

        repository.loadProfile { profile, _ ->
            val existingProfile = profile ?: UserProfile()
            val updatedProfile = existingProfile.copy(purposes = currentState.selectedPurposes.toList())

            repository.saveProfile(updatedProfile) { success, error ->
                if (success) {
                    _state.update { it.copy(isLoading = false, successMessage = "Данные сохранены!") }
                    viewModelScope.launch {
                        if (isChange) {
                            _effect.emit(PurposeEffect.NavigateBack)
                        } else {
                            _effect.emit(PurposeEffect.NavigateToWarning)
                        }
                    }
                } else {
                    _state.update { it.copy(isLoading = false, errorMessage = error) }
                }
            }
        }
    }
}