package io.github.lukasprediger.minimalcointosser.datastore

import android.content.Context
import androidx.annotation.StringRes
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.lukasprediger.minimalcointosser.R
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

enum class Theme(@StringRes val titleString: Int) {
    LIGHT(R.string.setting_theme_light),
    DARK(R.string.setting_theme_dark),
    SYSTEM(R.string.setting_theme_system);
}

private fun <T> DataStore<Preferences>.get(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
    this.data.map { it[key] ?: defaultValue }

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    val delay = dataStore.get(delayKey, 300)

    val keepOn = dataStore.get(keepOnKey, true)

    val theme = dataStore.get(themeKey, Theme.SYSTEM.name).map(Theme::valueOf)

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
    suspend fun changeTheme(theme: Theme) {
        dataStore.edit {
            it[themeKey] = theme.name
        }
    }

    companion object {
        private val delayKey = intPreferencesKey("delay")
        private val keepOnKey = booleanPreferencesKey("keepOn")
        private val themeKey = stringPreferencesKey("theme")
    }
}