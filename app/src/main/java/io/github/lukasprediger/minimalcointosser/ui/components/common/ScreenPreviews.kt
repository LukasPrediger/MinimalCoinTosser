package io.github.lukasprediger.minimalcointosser.ui.components.common

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(device = "id:pixel_6_pro", name = "Pixel6Pro Day", group = "Pixel 6 Pro", uiMode = UI_MODE_NIGHT_NO)
@Preview(device = "id:pixel_6_pro", name = "Pixel6Pro Night", group = "Pixel 6 Pro", uiMode = UI_MODE_NIGHT_YES)
@Preview(device = "spec:width=1280dp,height=800dp,dpi=480", name = "Tablet Day", group = "Tablet", uiMode = UI_MODE_NIGHT_NO)
@Preview(device = "spec:width=1280dp,height=800dp,dpi=480", name = "Tablet Night", group = "Tablet", uiMode = UI_MODE_NIGHT_YES)
@Preview(device = "spec:width=411dp,height=891dp", name = "Phone Day", group = "Phone", uiMode = UI_MODE_NIGHT_NO)
@Preview(device = "spec:width=411dp,height=891dp", name = "Phone Night", group = "Phone", uiMode = UI_MODE_NIGHT_YES)
annotation class ScreenPreviews()
