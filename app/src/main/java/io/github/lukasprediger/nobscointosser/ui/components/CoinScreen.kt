package io.github.lukasprediger.nobscointosser.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.lukasprediger.nobscointosser.R
import io.github.lukasprediger.nobscointosser.ui.theme.AppTheme

@Composable
fun CoinScreen(screenState: ScreenState, onButtonPressed: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .clickable(onClick = onButtonPressed)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.titleLarge) {
            when (screenState) {
                ScreenState.InitialState -> Text(stringResource(R.string.tosser_screen_open_title))
                ScreenState.LoadingState -> CircularProgressIndicator()
                is ScreenState.TossedState -> Text(stringResource(screenState.result.stringId))
            }
        }
    }
}

sealed class ScreenState {
    object InitialState : ScreenState()
    object LoadingState : ScreenState()
    data class TossedState(val result: CoinResult) : ScreenState()
}

enum class CoinResult(@StringRes val stringId: Int) {
    HEADS(R.string.tosser_screen_heads), TAILS(R.string.tosser_screen_tails);
}

@Preview(widthDp = 340, heightDp = 560, showBackground = true)
@Composable
fun CounScreenInitialPreview() {
    AppTheme {
        CoinScreen(ScreenState.InitialState) {}
    }
}

@Preview(widthDp = 340, heightDp = 560, showBackground = true)
@Composable
fun CounScreenLoadingPreview() {
    AppTheme {
        CoinScreen(ScreenState.LoadingState) {}
    }
}

@Preview(widthDp = 340, heightDp = 560, showBackground = true)
@Composable
fun CounScreenResultPreview() {
    AppTheme {
        CoinScreen(ScreenState.TossedState(CoinResult.HEADS)) {}
    }
}