package io.github.lukasprediger.nobscointosser.ui.components.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import io.github.lukasprediger.nobscointosser.ui.theme.dimensions

@Composable
@Destination
fun SettingsPage(navigator: DestinationsNavigator, viewModel: SettingsViewModel = hiltViewModel()) {
    val keepOn by viewModel.keepOn.collectAsState(true)

    SettingsScreen(
        delayText = viewModel.delayText,
        onDelayChange = viewModel::changeDelayText,
        delayError = viewModel.delayError,
        keepOn = keepOn,
        onKeepOnChanged = viewModel::changeKeepOn
    ) { navigator.navigateUp() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    delayText: String,
    onDelayChange: (String) -> Unit,
    delayError: Boolean,
    keepOn: Boolean,
    onKeepOnChanged: (Boolean) -> Unit,
    onBackButtonClick: () -> Unit
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
            KeepOnSettingRow(keepOn, onKeepOnChanged)
        }
    }
}

@Composable
fun KeepOnSettingRow(keepOn: Boolean, onKeepOnChanged: (Boolean) -> Unit) {
    SettingRow(
        title = stringResource(R.string.setting_keep_on_title),
        description = stringResource(R.string.setting_keep_on_description)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.gapL),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(checked = keepOn, onCheckedChange = onKeepOnChanged)
            Text(
                stringResource(R.string.setting_keep_on_title),
                style = MaterialTheme.typography.labelLarge
            )
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
        SettingsScreen(
            delayText = "0",
            onDelayChange = {},
            delayError = false,
            keepOn = true,
            onKeepOnChanged = {},
            onBackButtonClick = {}
        )
    }
}


@Preview(widthDp = 340, heightDp = 560, showBackground = true)
@Composable
fun SettingsScreenErrorPreview() {
    AppTheme {
        SettingsScreen(
            delayText = "",
            onDelayChange = {},
            delayError = true,
            keepOn = true,
            onKeepOnChanged = {},
            onBackButtonClick = {}
        )
    }
}