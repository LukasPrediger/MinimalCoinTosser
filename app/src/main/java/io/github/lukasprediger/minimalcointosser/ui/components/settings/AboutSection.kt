package io.github.lukasprediger.minimalcointosser.ui.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.lukasprediger.minimalcointosser.BuildConfig
import io.github.lukasprediger.minimalcointosser.R
import io.github.lukasprediger.minimalcointosser.ui.theme.AppTheme
import io.github.lukasprediger.minimalcointosser.ui.theme.dimensions

@Composable
fun AboutSection() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimensions.gapL),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.gapL)
    ) {
        Text("About", style = typography.titleLarge)
        Text("${stringResource(R.string.app_name)} by: Lukas Prediger")
        Text("App Version: ${BuildConfig.VERSION_NAME}")
        Text("This foss app contains no ads or tracking")
        LinkButtonRow(
            label = "App Source on",
            icon = painterResource(R.drawable.github),
            contentDescription = "The github logo",
            buttonText = "Github",
            uri = "https://github.com/LukasPrediger/MinimalCoinTosser"
        )

        LinkButtonRow(
            label = "App Icon provided by",
            buttonText = "SVG Repo",
            uri = "https://www.svgrepo.com/svg/477430/coin-toss-3"
        )

        LinkButton(
            icon = painterResource(R.drawable.baseline_bug_report_24),
            text = "Report an Issue",
            uri = "https://github.com/LukasPrediger/MinimalCoinTosser/issues"
        )
    }
}

@Composable
fun LinkButtonRow(
    label: String,
    icon: Painter? = null,
    contentDescription: String? = null,
    buttonText: String,
    uri: String
) {
    val localUriHandler = LocalUriHandler.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = ButtonDefaults.MinHeight, minWidth = ButtonDefaults.MinWidth)
            .height(IntrinsicSize.Min)
            .clickable { localUriHandler.openUri(uri) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            Modifier
                .background(
                    color = colorScheme.secondary,
                    shape = MaterialTheme.shapes.small.copy(
                        topEnd = ZeroCornerSize,
                        bottomEnd = ZeroCornerSize
                    )
                )
                .fillMaxHeight()
                .padding(MaterialTheme.dimensions.gapM)
                .weight(1f),
        ) {
            Text(label, color = colorScheme.onSecondary)
        }
        Row(
            Modifier
                .background(
                    color = colorScheme.primary,
                    shape = MaterialTheme.shapes.small.copy(
                        topStart = ZeroCornerSize,
                        bottomStart = ZeroCornerSize
                    )
                )
                .fillMaxHeight()
                .padding(MaterialTheme.dimensions.gapM),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.gapS),
        ) {
            icon?.let {
                Icon(
                    painter = it,
                    contentDescription = contentDescription,
                    tint = colorScheme.onPrimary
                )
            }
            Text(buttonText, color = colorScheme.onPrimary)
        }
    }
}

@Composable
fun LinkButton(
    icon: Painter? = null,
    contentDescription: String? = null,
    text: String,
    uri: String
) {
    val localUriHandler = LocalUriHandler.current

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { localUriHandler.openUri(uri) },
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.gapS)
        ) {
            icon?.let { Icon(painter = it, contentDescription = contentDescription) }
            Text(text)
        }
    }
}

@Preview(widthDp = 340, heightDp = 560, showBackground = true)
@Composable
fun AboutSectionPreview() {
    AppTheme {
        AboutSection()
    }
}