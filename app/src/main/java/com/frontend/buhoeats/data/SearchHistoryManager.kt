package com.frontend.buhoeats.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
private val Context.dataStore by preferencesDataStore(name = "search_history")

class SearchHistoryManager(private val context: Context) {

    private fun getKeyForUser(userId: String) = stringSetPreferencesKey("search_history_user_$userId")

    fun getSearchHistoryFlow(userId: String): Flow<List<String>> = context.dataStore.data
        .map { preferences ->
            preferences[getKeyForUser(userId)]?.toList() ?: emptyList()
        }

    suspend fun addSearchItem(userId: String, item: String) {
        context.dataStore.edit { preferences ->
            val currentSet = preferences[getKeyForUser(userId)] ?: emptySet()
            val updatedSet = (setOf(item) + currentSet).take(10).toSet()
            preferences[getKeyForUser(userId)] = updatedSet
        }
    }

    suspend fun removeSearchItem(userId: String, item: String) {
        context.dataStore.edit { preferences ->
            val currentSet = preferences[getKeyForUser(userId)] ?: emptySet()
            preferences[getKeyForUser(userId)] = currentSet.filter { it != item }.toSet()
        }
    }
}

