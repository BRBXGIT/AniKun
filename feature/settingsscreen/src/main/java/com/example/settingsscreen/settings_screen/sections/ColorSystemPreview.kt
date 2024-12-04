package com.example.settingsscreen.settings_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.darkDaiquiriScheme
import com.example.designsystem.theme.darkGreenAppleScheme
import com.example.designsystem.theme.darkSakuraScheme
import com.example.designsystem.theme.darkScheme
import com.example.designsystem.theme.darkTacosScheme
import com.example.designsystem.theme.lightDaiquiriScheme
import com.example.designsystem.theme.lightGreenAppleScheme
import com.example.designsystem.theme.lightSakuraScheme
import com.example.designsystem.theme.lightScheme
import com.example.designsystem.theme.lightTacosScheme
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography

data class ColorSystemPreviewColors(
    val background: Color,
    val surfaceContainerHighest: Color,
    val surfaceContainer: Color,
    val onSecondaryContainer: Color,
    val primaryContainer: Color,
    val name: String,
    val previewName: String
)

@Composable
fun ColorSystemElements(
    chosenTheme: String,
    chosenColorSystem: String,
    onColorSystemClick: (String) -> Unit
) {
    val darkColorSystems = listOf(
        ColorSystemPreviewColors(
            background = darkScheme.background,
            surfaceContainerHighest = darkScheme.surfaceContainerHighest,
            surfaceContainer = darkScheme.surfaceContainer,
            onSecondaryContainer = darkScheme.onSecondaryContainer,
            primaryContainer = darkScheme.primaryContainer,
            name = "darkScheme",
            previewName = "Default"
        ),
        ColorSystemPreviewColors(
            background = darkGreenAppleScheme.background,
            surfaceContainerHighest = darkGreenAppleScheme.surfaceContainerHighest,
            surfaceContainer = darkGreenAppleScheme.surfaceContainer,
            onSecondaryContainer = darkGreenAppleScheme.onSecondaryContainer,
            primaryContainer = darkGreenAppleScheme.primaryContainer,
            name = "darkGreenApple",
            previewName = "Green apple"
        ),
        ColorSystemPreviewColors(
            background = darkSakuraScheme.background,
            surfaceContainerHighest = darkSakuraScheme.surfaceContainerHighest,
            surfaceContainer = darkSakuraScheme.surfaceContainer,
            onSecondaryContainer = darkSakuraScheme.onSecondaryContainer,
            primaryContainer = darkSakuraScheme.primaryContainer,
            name = "darkSakura",
            previewName = "Sakura"
        ),
        ColorSystemPreviewColors(
            background = darkDaiquiriScheme.background,
            surfaceContainerHighest = darkDaiquiriScheme.surfaceContainerHighest,
            surfaceContainer = darkDaiquiriScheme.surfaceContainer,
            onSecondaryContainer = darkDaiquiriScheme.onSecondaryContainer,
            primaryContainer = darkDaiquiriScheme.primaryContainer,
            name = "darkDaiquiri",
            previewName = "Daiquiri"
        ),
        ColorSystemPreviewColors(
            background = darkTacosScheme.background,
            surfaceContainerHighest = darkTacosScheme.surfaceContainerHighest,
            surfaceContainer = darkTacosScheme.surfaceContainer,
            onSecondaryContainer = darkTacosScheme.onSecondaryContainer,
            primaryContainer = darkTacosScheme.primaryContainer,
            name = "darkTacos",
            previewName = "Tacos"
        ),
    )

    val lightColorSystems = listOf(
        ColorSystemPreviewColors(
            background = lightScheme.background,
            surfaceContainerHighest = lightScheme.surfaceContainerHighest,
            surfaceContainer = lightScheme.surfaceContainer,
            onSecondaryContainer = lightScheme.onSecondaryContainer,
            primaryContainer = lightScheme.primaryContainer,
            name = "light",
            previewName = "Default"
        ),
        ColorSystemPreviewColors(
            background = lightGreenAppleScheme.background,
            surfaceContainerHighest = lightGreenAppleScheme.surfaceContainerHighest,
            surfaceContainer = lightGreenAppleScheme.surfaceContainer,
            onSecondaryContainer = lightGreenAppleScheme.onSecondaryContainer,
            primaryContainer = lightGreenAppleScheme.primaryContainer,
            name = "lightGreenApple",
            previewName = "Green apple"
        ),
        ColorSystemPreviewColors(
            background = lightSakuraScheme.background,
            surfaceContainerHighest = lightSakuraScheme.surfaceContainerHighest,
            surfaceContainer = lightSakuraScheme.surfaceContainer,
            onSecondaryContainer = lightSakuraScheme.onSecondaryContainer,
            primaryContainer = lightSakuraScheme.primaryContainer,
            name = "lightSakura",
            previewName = "Sakura"
        ),
        ColorSystemPreviewColors(
            background = lightDaiquiriScheme.background,
            surfaceContainerHighest = lightDaiquiriScheme.surfaceContainerHighest,
            surfaceContainer = lightDaiquiriScheme.surfaceContainer,
            onSecondaryContainer = lightDaiquiriScheme.onSecondaryContainer,
            primaryContainer = lightDaiquiriScheme.primaryContainer,
            name = "lightDaiquiri",
            previewName = "Daiquiri"
        ),
        ColorSystemPreviewColors(
            background = lightTacosScheme.background,
            surfaceContainerHighest = lightTacosScheme.surfaceContainerHighest,
            surfaceContainer = lightTacosScheme.surfaceContainer,
            onSecondaryContainer = lightTacosScheme.onSecondaryContainer,
            primaryContainer = lightTacosScheme.primaryContainer,
            name = "lightTacos",
            previewName = "Tacos"
        )
    )

    val darkThemeBySystem = isSystemInDarkTheme()
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            horizontal = 16.dp
        )
    ) {
        when(chosenTheme) {
            "default" -> if(darkThemeBySystem) {
                items(darkColorSystems) { colorSystem ->
                    ColorSystemPreview(
                        background = colorSystem.background,
                        surfaceContainerHighest = colorSystem.surfaceContainerHighest,
                        surfaceContainer = colorSystem.surfaceContainer,
                        onSecondaryContainer = colorSystem.onSecondaryContainer,
                        primaryContainer = colorSystem.primaryContainer,
                        chosenColorSystem = chosenColorSystem,
                        name = colorSystem.name,
                        previewName = colorSystem.previewName,
                        onClick = { onColorSystemClick(colorSystem.name) }
                    )
                }
            } else {
                items(lightColorSystems) { colorSystem ->
                    ColorSystemPreview(
                        background = colorSystem.background,
                        surfaceContainerHighest = colorSystem.surfaceContainerHighest,
                        surfaceContainer = colorSystem.surfaceContainer,
                        onSecondaryContainer = colorSystem.onSecondaryContainer,
                        primaryContainer = colorSystem.primaryContainer,
                        chosenColorSystem = chosenColorSystem,
                        name = colorSystem.name,
                        previewName = colorSystem.previewName,
                        onClick = { onColorSystemClick(colorSystem.name) }
                    )
                }
            }
            "light" -> {
                items(lightColorSystems) { colorSystem ->
                    ColorSystemPreview(
                        background = colorSystem.background,
                        surfaceContainerHighest = colorSystem.surfaceContainerHighest,
                        surfaceContainer = colorSystem.surfaceContainer,
                        onSecondaryContainer = colorSystem.onSecondaryContainer,
                        primaryContainer = colorSystem.primaryContainer,
                        chosenColorSystem = chosenColorSystem,
                        name = colorSystem.name,
                        previewName = colorSystem.previewName,
                        onClick = { onColorSystemClick(colorSystem.name) }
                    )
                }
            }
            "dark" -> {
                items(darkColorSystems) { colorSystem ->
                    ColorSystemPreview(
                        background = colorSystem.background,
                        surfaceContainerHighest = colorSystem.surfaceContainerHighest,
                        surfaceContainer = colorSystem.surfaceContainer,
                        onSecondaryContainer = colorSystem.onSecondaryContainer,
                        primaryContainer = colorSystem.primaryContainer,
                        chosenColorSystem = chosenColorSystem,
                        name = colorSystem.name,
                        previewName = colorSystem.previewName,
                        onClick = { onColorSystemClick(colorSystem.name) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ColorSystemPreview(
    background: Color,
    surfaceContainerHighest: Color,
    surfaceContainer: Color,
    onSecondaryContainer: Color,
    primaryContainer: Color,
    chosenColorSystem: String,
    name: String,
    previewName: String,
    onClick: () -> Unit
) {
    val chosen = chosenColorSystem == name

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(110.dp)
                .height(180.dp)
                .background(
                    shape = mShapes.small,
                    color = background
                )
                .border(
                    width = if (chosen) 2.dp else 1.dp,
                    color = mColors.secondary,
                    shape = mShapes.small
                )
                .clip(mShapes.small)
                .clickable {
                    onClick()
                }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(
                        color = surfaceContainerHighest,
                        shape = mShapes.small.copy(
                            bottomEnd = CornerSize(0.dp),
                            bottomStart = CornerSize(0.dp)
                        )
                    )
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(
                    vertical = 28.dp,
                    horizontal = 4.dp
                )
            ) {
                items(3) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .background(
                                shape = mShapes.extraSmall,
                                color = surfaceContainer
                            )
                    )
                }
            }

            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(
                        color = surfaceContainerHighest,
                        shape = mShapes.small.copy(
                            topEnd = CornerSize(0.dp),
                            topStart = CornerSize(0.dp)
                        )
                    )
                    .padding(end = 4.dp)
            ) {
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(
                        horizontal = 4.dp
                    )
                ) {
                    items(2) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(
                                    shape = CircleShape,
                                    color = onSecondaryContainer
                                )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .align(Alignment.CenterEnd)
                        .background(
                            shape = mShapes.extraSmall,
                            color = primaryContainer
                        )
                )
            }
        }

        Text(
            text = previewName,
            style = mTypography.labelMedium
        )
    }
}