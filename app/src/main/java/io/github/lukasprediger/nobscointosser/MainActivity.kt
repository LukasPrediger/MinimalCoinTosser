package io.github.lukasprediger.nobscointosser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import io.github.lukasprediger.nobscointosser.ui.components.CoinResult
import io.github.lukasprediger.nobscointosser.ui.components.CoinScreen
import io.github.lukasprediger.nobscointosser.ui.components.ScreenState
import io.github.lukasprediger.nobscointosser.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                var state by remember { mutableStateOf<ScreenState>(ScreenState.InitialState) }

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CoinScreen(screenState = state) {
                        if (state == ScreenState.LoadingState) return@CoinScreen

                        lifecycleScope.launch {
                            state = ScreenState.LoadingState
                            delay(500.milliseconds)
                            state = ScreenState.TossedState(
                                if (Random.nextBoolean()) CoinResult.HEADS
                                else CoinResult.TAILS
                            )
                        }
                    }
                }
            }
        }
    }
}