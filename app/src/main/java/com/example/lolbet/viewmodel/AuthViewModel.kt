package com.example.lolbet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    // On utilise LiveData ou StateFlow pour observer les changements
    val email = MutableLiveData<String>()
}