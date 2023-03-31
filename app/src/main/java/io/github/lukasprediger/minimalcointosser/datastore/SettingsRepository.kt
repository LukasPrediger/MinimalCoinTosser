package io.github.lukasprediger.minimalcointosser.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.lukasprediger.minimalcointosser.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {
    @Provides
    fun settingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepository(context.dataStore)
    }
}

private fun <T> DataStore<Preferences>.get(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
    this.data.map { it[key] ?: defaultValue }

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    val delay = dataStore.get(delayKey, 500)

    val keepOn = dataStore.get(keepOnKey, true)

    suspend fun changeDelay(value: Int) {
        dataStore.edit {
            it[delayKey] = value
        }
    }

    suspend fun changeKeepOn(keepOn: Boolean) {
        dataStore.edit {
            it[keepOnKey] = keepOn
        }
    }

    companion object {
        private val delayKey = intPreferencesKey("delay")
        private val keepOnKey = booleanPreferencesKey("keepOn")
    }
}