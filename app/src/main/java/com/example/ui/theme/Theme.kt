package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BrandOrangeLight,
    onPrimary = CharcoalText,
    primaryContainer = BrandOrangeDark,
    onPrimaryContainer = Color.White,
    secondary = BrandOrange,
    onSecondary = Color.White,
    background = DarkBg,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkBorder,
    error = AlertRed,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = BrandOrange,
    onPrimary = Color.White,
    primaryContainer = BrandOrangeGlow,
    onPrimaryContainer = BrandOrangeDark,
    secondary = CharcoalSecondary,
    onSecondary = Color.White,
    background = PureWhite,
    onBackground = CharcoalText,
    surface = OffWhite,
    onSurface = CharcoalText,
    surfaceVariant = LightGraySurface,
    onSurfaceVariant = CharcoalSecondary,
    outline = BorderGray,
    error = AlertRed,
    onError = Color.White
)

@Composable
fun CateringEgyptTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
