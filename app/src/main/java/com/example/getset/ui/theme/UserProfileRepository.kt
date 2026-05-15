package com.example.getset.ui.theme

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserProfileRepository {

    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private fun getUserId(): String? = auth.currentUser?.uid
    private fun getUserProfileRef() = getUserId()?.let {
        firestore.collection("userProfiles").document(it)
    }
    fun saveProfile(profile: UserProfile, onComplete: (Boolean, String?) -> Unit) {
        val userId = getUserId()
        if (userId == null) {
            onComplete(false, "Пользователь не авторизован")
            return
        }

        getUserProfileRef()?.set(profile)
            ?.addOnSuccessListener {
                onComplete(true, null)
            }
            ?.addOnFailureListener { e ->
                onComplete(false, e.message)
            }
    }
    fun loadProfile(onComplete: (UserProfile?, String?) -> Unit) {
        val userId = getUserId()
        if (userId == null) {
            onComplete(null, "Пользователь не авторизован")
            return
        }
        getUserProfileRef()?.get()
            ?.addOnSuccessListener { document ->
                if (document.exists()) {
                    val profile = document.toObject(UserProfile::class.java)
                    onComplete(profile, null)
                } else {
                    onComplete(null, null)
                }
            }
            ?.addOnFailureListener { e ->
                onComplete(null, e.message)
            }
    }
}
