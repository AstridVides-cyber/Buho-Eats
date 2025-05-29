package com.frontend.buhoeats.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "search_history")

class SearchHistoryManager(private val context: Context) {

    private val SEARCH_HISTORY_KEY = stringSetPreferencesKey("search_history")

    val searchHistoryFlow: Flow<List<String>> = context.dataStore.data.map { preferences ->
        preferences[SEARCH_HISTORY_KEY]?.toList() ?: emptyList()
    }

    suspend fun addSearchItem(item: String) {
        context.dataStore.edit { preferences ->
            val currentSet = preferences[SEARCH_HISTORY_KEY] ?: emptySet()
            preferences[SEARCH_HISTORY_KEY] = setOf(item) + currentSet
        }
    }
}
