package com.nsa.ui.theme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val md_theme_light_primary = Color(0xFFFF3578)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFFFD9DD)
val md_theme_light_onPrimaryContainer = Color(0xFF400012)
val md_theme_light_secondary = Color(0xFFBC0051)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFFFD9DF)
val md_theme_light_onSecondaryContainer = Color(0xFF3F0016)
val md_theme_light_tertiary = Color(0xFF9C404B)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFFFDADB)
val md_theme_light_onTertiaryContainer = Color(0xFF40000E)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFF8FDFF)
val md_theme_light_onBackground = Color(0xFF001F25)
val md_theme_light_surface = Color(0xFFF8FDFF)
val md_theme_light_onSurface = Color(0xFF001F25)
val md_theme_light_surfaceVariant = Color(0xFFFFFFFF)
val md_theme_light_onSurfaceVariant = Color(0xFF524344)
val md_theme_light_outline = Color(0xFF847374)
val md_theme_light_inverseOnSurface = Color(0xFFD6F6FF)
val md_theme_light_inverseSurface = Color(0xFF252527)
val md_theme_light_inversePrimary = Color(0xFFFFB2BB)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFFBD0045)
val md_theme_light_outlineVariant = Color(0xFFD7C1C3)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFFFB2BB)
val md_theme_dark_onPrimary = Color(0xFF670022)
val md_theme_dark_primaryContainer = Color(0xFF910033)
val md_theme_dark_onPrimaryContainer = Color(0xFFFFD9DD)
val md_theme_dark_secondary = Color(0xFFFFB1C0)
val md_theme_dark_onSecondary = Color(0xFF660028)
val md_theme_dark_secondaryContainer = Color(0xFF90003C)
val md_theme_dark_onSecondaryContainer = Color(0xFFFFD9DF)
val md_theme_dark_tertiary = Color(0xFFFFB2B7)
val md_theme_dark_onTertiary = Color(0xFF5F1220)
val md_theme_dark_tertiaryContainer = Color(0xFF7D2935)
val md_theme_dark_onTertiaryContainer = Color(0xFFFFDADB)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF001F25)
val md_theme_dark_onBackground = Color(0xFFA6EEFF)
val md_theme_dark_surface = Color(0xFF001F25)
val md_theme_dark_onSurface = Color(0xFFA6EEFF)
val md_theme_dark_surfaceVariant = Color(0xFF524344)
val md_theme_dark_onSurfaceVariant = Color(0xFFD7C1C3)
val md_theme_dark_outline = Color(0xFF9F8C8E)
val md_theme_dark_inverseOnSurface = Color(0xFF001F25)
val md_theme_dark_inverseSurface = Color(0xFFA6EEFF)
val md_theme_dark_inversePrimary = Color(0xFFBD0045)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFFFB2BB)
val md_theme_dark_outlineVariant = Color(0xFF524344)
val md_theme_dark_scrim = Color(0xFF000000)

val seed = Color(0xFF06283D)


// Login Screen Color
val white_button_container_color = Color(0xffffffff)
val white_button_content_color = Color(0xff000E08)

// Find people screen color
val favorite_button_container_color = Color(0xffFFEDED)
val favorite_button_content_color = Color(0xffFF3578)
val favorite_button_container_enabled_color = Color(0xffFF0360)

val wave_button_container_color = Color(0xffFAFAFA)
val wave_button_content_color = Color(0xffFF3578)

@get:Composable
val ColorScheme.topAppBarColor: Color
    get() = seed

@get:Composable
val ColorScheme.lightGreen: Color
    get() = Color(0x9932CD32)

@get:Composable
val ColorScheme.red: Color
    get() = Color(0x99F44336)


@get:Composable
val ColorScheme.darkBackground: Color
    get() = seed