package com.example.compose
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val md_theme_light_primary = Color(0xFF954A03)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFFFDCC6)
val md_theme_light_onPrimaryContainer = Color(0xFF311300)
val md_theme_light_secondary = Color(0xFF006878)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFA5EEFF)
val md_theme_light_onSecondaryContainer = Color(0xFF001F25)
val md_theme_light_tertiary = Color(0xFF606134)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFE5E6AD)
val md_theme_light_onTertiaryContainer = Color(0xFF1C1D00)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF201A17)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF201A17)
val md_theme_light_surfaceVariant = Color(0xFFF4DED3)
val md_theme_light_onSurfaceVariant = Color(0xFF52443C)
val md_theme_light_outline = Color(0xFF84746A)
val md_theme_light_inverseOnSurface = Color(0xFFFBEEE8)
val md_theme_light_inverseSurface = Color(0xFF362F2B)
val md_theme_light_inversePrimary = Color(0xFFFFB786)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF954A03)
val md_theme_light_outlineVariant = Color(0xFFD7C3B7)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFFFB786)
val md_theme_dark_onPrimary = Color(0xFF502400)
val md_theme_dark_primaryContainer = Color(0xFF723600)
val md_theme_dark_onPrimaryContainer = Color(0xFFFFDCC6)
val md_theme_dark_secondary = Color(0xFF4CD7F2)
val md_theme_dark_onSecondary = Color(0xFF00363F)
val md_theme_dark_secondaryContainer = Color(0xFF004E5A)
val md_theme_dark_onSecondaryContainer = Color(0xFFA5EEFF)
val md_theme_dark_tertiary = Color(0xFFC9CA93)
val md_theme_dark_onTertiary = Color(0xFF31320A)
val md_theme_dark_tertiaryContainer = Color(0xFF48491F)
val md_theme_dark_onTertiaryContainer = Color(0xFFE5E6AD)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF201A17)
val md_theme_dark_onBackground = Color(0xFFECE0DA)
val md_theme_dark_surface = Color(0xFF201A17)
val md_theme_dark_onSurface = Color(0xFFECE0DA)
val md_theme_dark_surfaceVariant = Color(0xFF52443C)
val md_theme_dark_onSurfaceVariant = Color(0xFFD7C3B7)
val md_theme_dark_outline = Color(0xFF9F8D83)
val md_theme_dark_inverseOnSurface = Color(0xFF201A17)
val md_theme_dark_inverseSurface = Color(0xFFECE0DA)
val md_theme_dark_inversePrimary = Color(0xFF954A03)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFFFB786)
val md_theme_dark_outlineVariant = Color(0xFF52443C)
val md_theme_dark_scrim = Color(0xFF000000)


val seed = Color(0xFFDF915A)


// OLD

val LightBackground = Color(0xFFFFFFFF)
val DarkBackground = Color(0xFF000000)

val LightTextColor = Color(0xFF000000)
val DarkTextColor = Color(0xFFFFFFFF)

val AvatarColors: List<Color> = listOf(
    Color(0xffa05203),
    Color(0xff034f22),
    Color(0xff15157a),
    Color(0xFF7A0010),
    Color(0xFF40270B),
    Color(0xFF88304a)
)

val bookarooPrimaryColor = Color(0xFFDF915A)


@Composable
fun getBackgroundColor() = if (isSystemInDarkTheme()) DarkBackground else LightBackground

@Composable
fun basicTextColor(): Color = if (isSystemInDarkTheme()) DarkTextColor else LightTextColor

@Composable
fun getTintColor() = if (isSystemInDarkTheme()) Color.White else Color.Black

@Composable
fun getTintAltColor() = if (isSystemInDarkTheme()) Color.Gray else Color.LightGray

