package io.github.lukasprediger.nobscointosser.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.lukasprediger.nobscointosser.dataStore
import kotlinx.coroutines.flow.map

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {
    @Provides
    fun settingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepository(context.dataStore)
    }
}

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    val delay = dataStore.data.map { prefs ->
        prefs[delayKey] ?: 500
    }

    suspend fun changeDelay(value: Int) {
        dataStore.edit {
            it[delayKey] = value
        }
    }

    companion object {
        private val delayKey = intPreferencesKey("delay")
    }
}