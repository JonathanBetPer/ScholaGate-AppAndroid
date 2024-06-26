package me.scholagate.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = SgAzul,
    onPrimary = Color.White,
    inversePrimary = SgNaranja,

    secondary = SgNaranja,
    onSecondary = Color.White,


    tertiary = SgGris,
    onTertiary = Color.White,

    background = Color.White,
    onBackground = SgAzulOscuro,

    surfaceTint = SgGris,
    onSurface = SgAzulOscuro,
    surface = SgGris,
)

val LightColorScheme = lightColorScheme(
    primary = SgAzul,
    onPrimary = Color.White,

    secondary = SgNaranja,
    onSecondary = Color.White,

    tertiary = SgGris,
    onTertiary = Color.White,

    background = Color.White,
    onBackground = SgAzulOscuro,

    surfaceTint = Color.Green,
    onSurface = SgAzulOscuro,
    surface = SgGris,
)

@Composable
fun ScholaGateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        /*
         dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
             val context = LocalContext.current
             if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
         }
        */
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            window.navigationBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}