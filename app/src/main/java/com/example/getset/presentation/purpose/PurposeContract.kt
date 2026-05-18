package com.example.getset.presentation.purpose

data class PurposeState(
    val selectedPurposes: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val isDataLoaded: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isReadyToNext: Boolean = false
)

sealed class PurposeIntent {
    object LoadPurposes : PurposeIntent()
    data class TogglePurpose(val purpose: String) : PurposeIntent()
    object SavePurposes : PurposeIntent()
    object ChangePurposes : PurposeIntent()
    object DismissError : PurposeIntent()
}

sealed class PurposeEffect {
    object NavigateToWarning : PurposeEffect()
    object NavigateBack : PurposeEffect()
    data class ShowError(val message: String) : PurposeEffect()
}