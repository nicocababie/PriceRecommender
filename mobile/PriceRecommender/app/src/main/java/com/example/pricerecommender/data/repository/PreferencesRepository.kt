package com.example.pricerecommender.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    val userAddress: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading user address", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { storage ->
            storage[USER_ADDRESS] ?: ""
        }

    val userRange: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading user range", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { storage ->
            storage[USER_RANGE] ?: ""
        }

    val userId: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading user id", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { storage ->
            storage[USER_ID] ?: ""
        }

    private companion object {
        val USER_ADDRESS = stringPreferencesKey("user_address")
        val USER_RANGE = stringPreferencesKey("user_range")
        val USER_ID = stringPreferencesKey("user_id")
        const val TAG = "PreferencesRepository"
    }

    suspend fun saveUserAddress(text: String) {
        dataStore.edit { storage ->
            storage[USER_ADDRESS] = text
        }
    }

    suspend fun saveUserRange(range: Float) {
        dataStore.edit { storage ->
            storage[USER_RANGE] = range.toString()
        }
    }

    suspend fun saveUserId(id: String) {
        dataStore.edit { storage ->
            storage[USER_ID] = id
        }
    }
}