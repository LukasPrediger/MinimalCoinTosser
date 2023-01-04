package io.github.lukasprediger.nobscointosser.ui.components.tosser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.lukasprediger.nobscointosser.datastore.SettingsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class TosserViewModel @Inject constructor(settingsRepository: SettingsRepository) : ViewModel() {
    private val delay = settingsRepository.duration.stateIn(viewModelScope, SharingStarted.Eagerly, 500.milliseconds)

    var screenState by mutableStateOf<TosserScreenState>(TosserScreenState.InitialState)
        private set

    fun tossCoin() {
        if (screenState == TosserScreenState.LoadingState) return

        viewModelScope.launch {
            screenState = TosserScreenState.LoadingState
            delay(delay.value)
            screenState = TosserScreenState.TossedState(
                if (Random.nextBoolean()) CoinResult.HEADS
                else CoinResult.TAILS
            )
        }
    }
}