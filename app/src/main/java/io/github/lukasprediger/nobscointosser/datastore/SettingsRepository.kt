package io.github.lukasprediger.nobscointosser.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.lukasprediger.nobscointosser.dataStore
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {
    @Provides
    fun settingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepository(context.dataStore)
    }
}

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    val durationValue = dataStore.data.map { prefs ->
        prefs[durationValueKey] ?: 500
    }

    val durationUnit = dataStore.data.map { prefs ->
        prefs[durationUnitKey]?.let(DurationUnit::valueOf) ?: DurationUnit.MILLISECONDS
    }

    val duration = durationValue.combine(durationUnit) { value, unit -> value.toDuration(unit) }

    suspend fun changeDurationValue(value: Int) {
        dataStore.edit {
            it[durationValueKey] = value
        }
    }

    suspend fun changeDurationUnit(unit: DurationUnit) {
        dataStore.edit {
            it[durationUnitKey] = unit.name
        }
    }

    val durationValueKey = intPreferencesKey("duration_value")
    val durationUnitKey = stringPreferencesKey("duration_unit")
}