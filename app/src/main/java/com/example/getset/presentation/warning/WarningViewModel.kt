package com.example.getset.presentation.warning

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

class WarningViewModel(
    private val repository: UserProfileRepository = UserProfileRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(WarningState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<WarningEffect>()
    val effect = _effect.asSharedFlow()

    val allAreas = listOf(
        "Спина",
        "Руки",
        "Грудь",
        "Ноги",
        "Ягодицы",
        "Пресс"
    )

    init {
        handleIntent(WarningIntent.LoadAreas)
    }

    fun handleIntent(intent: WarningIntent) {
        when (intent) {
            is WarningIntent.LoadAreas -> loadAreas()
            is WarningIntent.ToggleArea -> toggleArea(intent.area)
            is WarningIntent.SaveAreas -> saveAreas(false)
            is WarningIntent.ChangeAreas -> saveAreas(true)
            is WarningIntent.DismissError -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun loadAreas() {
        _state.update { it.copy(isLoading = true, isDataLoaded = false) }
        repository.loadProfile { profile, error ->
            if (profile != null) {
                _state.update { 
                    it.copy(
                        selectedAreas = profile.attentionAreas.toSet(),
                        isLoading = false,
                        isDataLoaded = true,
                        isReadyToNext = profile.attentionAreas.isNotEmpty()
                    ) 
                }
            } else {
                _state.update { it.copy(isLoading = false, isDataLoaded = true, errorMessage = error) }
            }
        }
    }

    private fun toggleArea(area: String) {
        _state.update { currentState ->
            val newSelected = if (currentState.selectedAreas.contains(area)) {
                currentState.selectedAreas - area
            } else {
                currentState.selectedAreas + area
            }
            currentState.copy(
                selectedAreas = newSelected,
                isReadyToNext = newSelected.isNotEmpty()
            )
        }
    }

    private fun saveAreas(isChange: Boolean) {
        val currentState = _state.value
        if (!currentState.isReadyToNext || currentState.isLoading) return

        _state.update { it.copy(isLoading = true, errorMessage = null, successMessage = null) }

        repository.loadProfile { profile, _ ->
            val existingProfile = profile ?: UserProfile()
            val updatedProfile = existingProfile.copy(attentionAreas = currentState.selectedAreas.toList())

            repository.saveProfile(updatedProfile) { success, error ->
                if (success) {
                    _state.update { it.copy(isLoading = false, successMessage = "Данные сохранены!") }
                    viewModelScope.launch {
                        if (isChange) {
                            _effect.emit(WarningEffect.NavigateBack)
                        } else {
                            _effect.emit(WarningEffect.NavigateToData)
                        }
                    }
                } else {
                    _state.update { it.copy(isLoading = false, errorMessage = error) }
                }
            }
        }
    }
}