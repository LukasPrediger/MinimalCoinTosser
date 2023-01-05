package io.github.lukasprediger.nobscointosser.ui.components.settings

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import io.github.lukasprediger.nobscointosser.MainActivity
import io.github.lukasprediger.nobscointosser.datastore.SettingsRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    var delayText by mutableStateOf("")
        private set

    var delayError by mutableStateOf(false)
        private set

    val keepOn = settingsRepository.keepOn

    init {
        viewModelScope.launch {
            settingsRepository.delay.collectLatest { delayText = it.toString() }
        }
    }

    fun changeDelayText(text: String) {
        delayText = text

        delayText.toIntOrNull()?.takeIf { it >= 0 }?.let {
            viewModelScope.launch {
                delayError = false
                settingsRepository.changeDelay(it)
            }
        } ?: kotlin.run {
            delayError = true
        }
    }

    fun changeKeepOn(keepOn: Boolean) {
        viewModelScope.launch {
            settingsRepository.changeKeepOn(keepOn)
        }
    }
}