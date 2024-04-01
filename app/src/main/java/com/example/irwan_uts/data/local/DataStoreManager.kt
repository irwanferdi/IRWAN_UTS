package com.example.irwan_uts.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreManager(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "USER_DATASTORE")
        val username = stringPreferencesKey("USERNAME")
        val password = stringPreferencesKey("PASSWORD")
        val github_username = stringPreferencesKey("GITHUB_USERNAME")
        val nim =  stringPreferencesKey("NIM")
        val email = stringPreferencesKey("EMAIL")

        val token = stringPreferencesKey("TOKEN")

    }

    suspend fun saveUserToDataStore(userData: UserData) {
        context.dataStore.edit { preferences ->
            preferences[username] = userData.username
            preferences[password] = userData.password
            preferences[github_username] = userData.githubUsername
            preferences[nim] = userData.nim
            preferences[email] = userData.email
        }
    }

    suspend fun saveToken(tokenKey: String){
        context.dataStore.edit { preferences ->
            preferences[token] = tokenKey
        }
    }

    fun getUserFromDataStore() = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            UserData(
                username = preferences[username] ?: "",
                password = preferences[password] ?: "",
                githubUsername = preferences[github_username] ?: "",
                nim = preferences[nim] ?: "",
                email = preferences[email] ?: ""
            )
        }

    fun getToken(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val token = preferences[token] ?: ""
                token
            }
    }


}