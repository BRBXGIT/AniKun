package com.example.settingsscreen.settings_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.darkDaiquiriScheme
import com.example.designsystem.theme.darkGreenAppleScheme
import com.example.designsystem.theme.darkLavenderScheme
import com.example.designsystem.theme.darkSakuraScheme
import com.example.designsystem.theme.darkScheme
import com.example.designsystem.theme.darkTacosScheme
import com.example.designsystem.theme.lightDaiquiriScheme
import com.example.designsystem.theme.lightGreenAppleScheme
import com.example.designsystem.theme.lightLavenderScheme
import com.example.designsystem.theme.lightSakuraScheme
import com.example.designsystem.theme.lightScheme
import com.example.designsystem.theme.lightTacosScheme
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography

data class ColorSystemPreviewColors(
    val secondaryContainer: Color,
    val background: Color,
    val surfaceContainerHighest: Color,
    val secondary: Color,
    val onSecondaryContainer: Color,
    val primary: Color,
    val name: String,
    val previewName: String,
    val tertiary: Color
)

@Composable
fun ColorSystemElements(
    chosenTheme: String,
    chosenColorSystem: String,
    onColorSystemClick: (String) -> Unit
) {
    val darkColorSystems = listOf(
        ColorSystemPreviewColors(
            secondaryContainer = darkScheme.secondaryContainer,
            background = darkScheme.background,
            surfaceContainerHighest = darkScheme.surfaceContainerHighest,
            secondary = darkScheme.secondary,
            onSecondaryContainer = darkScheme.onSecondaryContainer,
            primary = darkScheme.primary,
            name = "darkScheme",
            previewName = "Default",
            tertiary = darkScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = darkGreenAppleScheme.secondaryContainer,
            background = darkGreenAppleScheme.background,
            surfaceContainerHighest = darkGreenAppleScheme.surfaceContainerHighest,
            secondary = darkGreenAppleScheme.secondary,
            onSecondaryContainer = darkGreenAppleScheme.onSecondaryContainer,
            primary = darkGreenAppleScheme.primary,
            name = "darkGreenApple",
            previewName = "Green apple",
            tertiary = darkGreenAppleScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = darkSakuraScheme.secondaryContainer,
            background = darkSakuraScheme.background,
            surfaceContainerHighest = darkSakuraScheme.surfaceContainerHighest,
            secondary = darkSakuraScheme.secondary,
            onSecondaryContainer = darkSakuraScheme.onSecondaryContainer,
            primary = darkSakuraScheme.primary,
            name = "darkSakura",
            previewName = "Sakura",
            tertiary = darkSakuraScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = darkDaiquiriScheme.secondaryContainer,
            background = darkDaiquiriScheme.background,
            surfaceContainerHighest = darkDaiquiriScheme.surfaceContainerHighest,
            secondary = darkDaiquiriScheme.secondary,
            onSecondaryContainer = darkDaiquiriScheme.onSecondaryContainer,
            primary = darkDaiquiriScheme.primary,
            name = "darkDaiquiri",
            previewName = "Daiquiri",
            tertiary = darkDaiquiriScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = darkTacosScheme.secondaryContainer,
            background = darkTacosScheme.background,
            surfaceContainerHighest = darkTacosScheme.surfaceContainerHighest,
            secondary = darkTacosScheme.secondary,
            onSecondaryContainer = darkTacosScheme.onSecondaryContainer,
            primary = darkTacosScheme.primary,
            name = "darkTacos",
            previewName = "Tacos",
            tertiary = darkTacosScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = darkLavenderScheme.secondaryContainer,
            background = darkLavenderScheme.background,
            surfaceContainerHighest = darkLavenderScheme.surfaceContainerHighest,
            secondary = darkLavenderScheme.secondary,
            onSecondaryContainer = darkLavenderScheme.onSecondaryContainer,
            primary = darkLavenderScheme.primary,
            name = "darkLavender",
            previewName = "Lavender",
            tertiary = darkLavenderScheme.tertiary
        ),
    )

    val lightColorSystems = listOf(
        ColorSystemPreviewColors(
            secondaryContainer = lightScheme.secondaryContainer,
            background = lightScheme.background,
            surfaceContainerHighest = lightScheme.surfaceContainerHighest,
            secondary = lightScheme.secondary,
            onSecondaryContainer = lightScheme.onSecondaryContainer,
            primary = lightScheme.primary,
            name = "light",
            previewName = "Default",
            tertiary = lightScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = lightGreenAppleScheme.secondaryContainer,
            background = lightGreenAppleScheme.background,
            surfaceContainerHighest = lightGreenAppleScheme.surfaceContainerHighest,
            secondary = lightGreenAppleScheme.secondary,
            onSecondaryContainer = lightGreenAppleScheme.onSecondaryContainer,
            primary = lightGreenAppleScheme.primary,
            name = "lightGreenApple",
            previewName = "Green apple",
            tertiary = lightGreenAppleScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = lightSakuraScheme.secondaryContainer,
            background = lightSakuraScheme.background,
            surfaceContainerHighest = lightSakuraScheme.surfaceContainerHighest,
            secondary = lightSakuraScheme.secondary,
            onSecondaryContainer = lightSakuraScheme.onSecondaryContainer,
            primary = lightSakuraScheme.primary,
            name = "lightSakura",
            previewName = "Sakura",
            tertiary = lightSakuraScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = lightDaiquiriScheme.secondaryContainer,
            background = lightDaiquiriScheme.background,
            surfaceContainerHighest = lightDaiquiriScheme.surfaceContainerHighest,
            secondary = lightDaiquiriScheme.secondary,
            onSecondaryContainer = lightDaiquiriScheme.onSecondaryContainer,
            primary = lightDaiquiriScheme.primary,
            name = "lightDaiquiri",
            previewName = "Daiquiri",
            tertiary = lightDaiquiriScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = lightTacosScheme.secondaryContainer,
            background = lightTacosScheme.background,
            surfaceContainerHighest = lightTacosScheme.surfaceContainerHighest,
            secondary = lightTacosScheme.secondary,
            onSecondaryContainer = lightTacosScheme.onSecondaryContainer,
            primary = lightTacosScheme.primary,
            name = "lightTacos",
            previewName = "Tacos",
            tertiary = lightTacosScheme.tertiary
        ),
        ColorSystemPreviewColors(
            secondaryContainer = lightLavenderScheme.secondaryContainer,
            background = lightLavenderScheme.background,
            surfaceContainerHighest = lightLavenderScheme.surfaceContainerHighest,
            secondary = lightLavenderScheme.secondary,
            onSecondaryContainer = lightLavenderScheme.onSecondaryContainer,
            primary = lightLavenderScheme.primary,
            name = "lightLavender",
            previewName = "Lavender",
            tertiary = lightLavenderScheme.tertiary
        ),
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
                        tertiary = colorSystem.tertiary,
                        secondaryContainer = colorSystem.secondaryContainer,
                        background = colorSystem.background,
                        surfaceContainerHighest = colorSystem.surfaceContainerHighest,
                        secondary = colorSystem.secondary,
                        onSecondaryContainer = colorSystem.onSecondaryContainer,
                        primary = colorSystem.primary,
                        chosenColorSystem = chosenColorSystem,
                        name = colorSystem.name,
                        previewName = colorSystem.previewName,
                        onClick = { onColorSystemClick(colorSystem.name) }
                    )
                }
            } else {
                items(lightColorSystems) { colorSystem ->
                    ColorSystemPreview(
                        tertiary = colorSystem.tertiary,
                        secondaryContainer = colorSystem.secondaryContainer,
                        background = colorSystem.background,
                        surfaceContainerHighest = colorSystem.surfaceContainerHighest,
                        secondary = colorSystem.secondary,
                        onSecondaryContainer = colorSystem.onSecondaryContainer,
                        primary = colorSystem.primary,
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
                        tertiary = colorSystem.tertiary,
                        secondaryContainer = colorSystem.secondaryContainer,
                        background = colorSystem.background,
                        surfaceContainerHighest = colorSystem.surfaceContainerHighest,
                        secondary = colorSystem.secondary,
                        onSecondaryContainer = colorSystem.onSecondaryContainer,
                        primary = colorSystem.primary,
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
                        tertiary = colorSystem.tertiary,
                        secondaryContainer = colorSystem.secondaryContainer,
                        background = colorSystem.background,
                        surfaceContainerHighest = colorSystem.surfaceContainerHighest,
                        secondary = colorSystem.secondary,
                        onSecondaryContainer = colorSystem.onSecondaryContainer,
                        primary = colorSystem.primary,
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
    tertiary: Color,
    secondaryContainer: Color,
    background: Color,
    surfaceContainerHighest: Color,
    secondary: Color,
    onSecondaryContainer: Color,
    primary: Color,
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
                    width = if(chosen) 2.dp else 1.dp,
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
                    horizontal = 8.dp
                )
            ) {
                items(3) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(
                                    height = 24.dp,
                                    width = 16.dp
                                )
                                .background(
                                    color = Color.Gray,
                                    shape = mShapes.extraSmall
                                )
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(4.dp)
                                    .fillMaxWidth()
                                    .background(
                                        color = primary,
                                        shape = RoundedCornerShape(1.dp)
                                    )
                            )

                            Box(
                                modifier = Modifier
                                    .height(3.dp)
                                    .fillMaxWidth(0.3f)
                                    .background(
                                        color = secondary,
                                        shape = RoundedCornerShape(1.dp)
                                    )
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(2.dp)
                                        .fillMaxWidth()
                                        .background(
                                            color = tertiary,
                                            shape = RoundedCornerShape(1.dp)
                                        )
                                )

                                Box(
                                    modifier = Modifier
                                        .height(2.dp)
                                        .fillMaxWidth()
                                        .background(
                                            color = tertiary,
                                            shape = RoundedCornerShape(1.dp)
                                        )
                                )
                            }
                        }
                    }
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
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    contentPadding = PaddingValues(
                        horizontal = 4.dp
                    )
                ) {
                    items(4) {
                        Box(
                            modifier = Modifier
                                .size(
                                    height = 8.dp,
                                    width = 12.dp
                                )
                                .background(
                                    shape = CircleShape,
                                    color = secondaryContainer
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(2.dp)
                                    .background(
                                        color = onSecondaryContainer,
                                        shape = CircleShape
                                    ),
                            )
                        }
                    }
                }
            }
        }

        Text(
            text = previewName,
            style = mTypography.labelMedium
        )
    }
}