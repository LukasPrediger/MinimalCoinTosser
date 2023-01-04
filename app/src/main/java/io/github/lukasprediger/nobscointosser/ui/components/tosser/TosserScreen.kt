package io.github.lukasprediger.nobscointosser.ui.components.tosser

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
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.lukasprediger.nobscointosser.R
import io.github.lukasprediger.nobscointosser.ui.theme.AppTheme

@Composable
fun TosserPage(tosserViewModel: TosserViewModel = viewModel()) {
    TosserScreen(tosserViewModel.screenState, tosserViewModel::tossCoin)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TosserScreen(screenState: TosserScreenState, onButtonPressed: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(
            Modifier.padding(paddingValues)
        ) {
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
                        TosserScreenState.InitialState -> Text(stringResource(R.string.tosser_screen_open_title))
                        TosserScreenState.LoadingState -> CircularProgressIndicator()
                        is TosserScreenState.TossedState -> Text(stringResource(screenState.result.stringId))
                    }
                }
            }
        }
    }
}

sealed class TosserScreenState {
    object InitialState : TosserScreenState()
    object LoadingState : TosserScreenState()
    data class TossedState(val result: CoinResult) : TosserScreenState()
}

enum class CoinResult(@StringRes val stringId: Int) {
    HEADS(R.string.tosser_screen_heads), TAILS(R.string.tosser_screen_tails);
}

@Preview(widthDp = 340, heightDp = 560, showBackground = true)
@Composable
fun TosserScreenInitialPreview() {
    AppTheme {
        TosserScreen(TosserScreenState.InitialState) {}
    }
}

@Preview(widthDp = 340, heightDp = 560, showBackground = true)
@Composable
fun TosserScreenLoadingPreview() {
    AppTheme {
        TosserScreen(TosserScreenState.LoadingState) {}
    }
}

@Preview(widthDp = 340, heightDp = 560, showBackground = true)
@Composable
fun TosserScreenResultPreview() {
    AppTheme {
        TosserScreen(TosserScreenState.TossedState(CoinResult.HEADS)) {}
    }
}