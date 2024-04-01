package com.example.irwan_uts.ui.auth.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import com.example.irwan_uts.data.local.DataStoreManager
import com.example.irwan_uts.data.local.UserData
import kotlinx.coroutines.launch

class RegisterViewModel (application: Application) : AndroidViewModel(application) {
    private val dataStore = DataStoreManager(application)

    fun saveUser(userData: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveUserToDataStore(userData)
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveToken(token)
        }
    }
}