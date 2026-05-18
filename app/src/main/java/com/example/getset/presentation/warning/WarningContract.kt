package com.example.getset.presentation.warning

data class WarningState(
    val selectedAreas: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val isDataLoaded: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isReadyToNext: Boolean = false
)

sealed class WarningIntent {
    object LoadAreas : WarningIntent()
    data class ToggleArea(val area: String) : WarningIntent()
    object SaveAreas : WarningIntent()
    object ChangeAreas : WarningIntent()
    object DismissError : WarningIntent()
}

sealed class WarningEffect {
    object NavigateToData : WarningEffect()
    object NavigateBack : WarningEffect()
    data class ShowError(val message: String) : WarningEffect()
}