package io.github.lukasprediger.nobscointosser.ui.components.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.lukasprediger.nobscointosser.R
import io.github.lukasprediger.nobscointosser.ui.theme.AppTheme

@Composable
@Destination
fun SettingsPage(navigator: DestinationsNavigator, viewModel: SettingsViewModel = hiltViewModel()) {
    SettingsScreen(
        delayText = viewModel.delayText,
        onDelayChange = viewModel::changeDelayText,
        delayError = viewModel.delayError
    ) { navigator.navigateUp() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    delayText: String,
    onDelayChange: (String) -> Unit,
    delayError: Boolean,
    onBackButtonClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.setting_title))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                navigationIcon = {
                    IconButton(onClick = onBackButtonClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Navigate Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
        ) {
            DurationSettingRow(delayText, onDelayChange, delayError)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DurationSettingRow(
    delayText: String,
    onDelayChange: (String) -> Unit,
    delayError: Boolean
) {
    SettingRow(
        title = stringResource(R.string.setting_delay_title),
        description = stringResource(R.string.setting_delay_description)
    ) {
        TextField(
            value = delayText,
            onValueChange = onDelayChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = delayError,
            supportingText = {
                if (delayError) {
                    Text(
                        stringResource(R.string.setting_delay_error),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            },
            visualTransformation = {
                TransformedText(
                    it + AnnotatedString(" ms"),
                    OffsetMapping.Identity
                )
            }
        )
    }
}


@Preview(widthDp = 340, heightDp = 560, showBackground = true)
@Composable
fun SettingsScreenPreview() {
    AppTheme {
        SettingsScreen("0", {}, false) {}
    }
}


@Preview(widthDp = 340, heightDp = 560, showBackground = true)
@Composable
fun SettingsScreenErrorPreview() {
    AppTheme {
        SettingsScreen("", {}, true) {}
    }
}