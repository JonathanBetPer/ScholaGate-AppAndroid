package me.scholagate.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = SgGris,
    onPrimary = SgAzulOscuro,

    secondary = SgAzul,
    onSecondary = Color.White,

    tertiary = SgNaranja,
    onTertiary = Color.White,

    background = Color.White,
    onBackground = SgAzulOscuro,

    surfaceTint = SgAzulOscuro,
    onSurface = SgGris,
)

val LightColorScheme = lightColorScheme(
    primary = SgGris,
    onPrimary = SgAzulOscuro,

    secondary = SgAzul,
    onSecondary = Color.White,

    tertiary = SgNaranja,
    onTertiary = Color.White,

    background = Color.White,
    onBackground = SgAzulOscuro,

    surfaceTint = SgGris,
    onSurface = SgAzulOscuro,
)

@Composable
fun ScholaGateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}