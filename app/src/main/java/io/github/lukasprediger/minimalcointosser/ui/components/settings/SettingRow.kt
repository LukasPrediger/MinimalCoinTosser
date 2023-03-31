package io.github.lukasprediger.minimalcointosser.ui.components.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.lukasprediger.minimalcointosser.ui.theme.AppTheme
import io.github.lukasprediger.minimalcointosser.ui.theme.dimensions

@Composable
fun SettingRow(title: String, description: String, content: @Composable ColumnScope.() -> Unit) {
  Column(Modifier.padding(MaterialTheme.dimensions.gapL)) {
      Text(title, style = MaterialTheme.typography.titleMedium )
      Text(
          description,
          style = MaterialTheme.typography.bodySmall,
          modifier = Modifier.padding(vertical = MaterialTheme.dimensions.gapS)
      )
      content()
  }
}
@Preview(widthDp = 340, heightDp = 560, showBackground = true)
@Composable
fun SettingRowPreview() {
  AppTheme {
    SettingRow("Awesome Setting", "This setting activates awesome mode.") {
        Row(verticalAlignment = Alignment.CenterVertically) {
           Switch(checked = false, onCheckedChange = {})
           Text(text = "Awesomesauce", Modifier.padding(start = MaterialTheme.dimensions.gapL))
        }
    }
  }
}