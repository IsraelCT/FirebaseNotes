package com.example.firebasenotes.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class LoginViewmodel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            Log.d("ERROR EN FIREBASE", "Usuario y contraseña incorrecta")
                        }
                    }
            } catch (e: Exception) {
                Log.d("ERROR EN JETPACK ", "Error: ${e.localizedMessage}")
            }
        }
    }
}
