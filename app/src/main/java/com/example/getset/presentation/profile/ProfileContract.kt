package com.example.getset.presentation.profile

data class ProfileState(
    val gender: String = "",
    val height: String = "",
    val myWeight: String = "",
    val wantWeight: String = "",
    val isLoading: Boolean = false,
    val isDataLoaded: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isFormValid: Boolean = false
)

sealed class ProfileIntent {
    object LoadProfile : ProfileIntent()
    data class UpdateGender(val gender: String) : ProfileIntent()
    data class UpdateHeight(val height: String) : ProfileIntent()
    data class UpdateMyWeight(val weight: String) : ProfileIntent()
    data class UpdateWantWeight(val weight: String) : ProfileIntent()
    object SaveProfile : ProfileIntent()
    object DismissError : ProfileIntent()
}

sealed class ProfileEffect {
    object NavigateBack : ProfileEffect()
    data class ShowToast(val message: String) : ProfileEffect()
}