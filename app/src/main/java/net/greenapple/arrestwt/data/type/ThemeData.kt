/* Author: Green Apple
 * Date Created: August 5, 2025
 * 
 * Other Dates:
 * > Refactored: August 16, 2025
 * 
 * Notes: Represents a theme and its data
 */

package net.greenapple.arrestwt.data.type

// ====== IMPORTS ======
import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import kotlinx.serialization.Serializable

// ====== THEME DATA ======
@Serializable
data class ThemeData(
  val name:                     String  = "Unnamed Theme",
  val baseSchemeDark:           Boolean = true,

  val primary:                  String? = null,
  val onPrimary:                String? = null, 
  val primaryContainer:         String? = null,
  val onPrimaryContainer:       String? = null,
  val inversePrimary:           String? = null,

  val secondary:                String? = null,
  val onSecondary:              String? = null,
  val secondaryContainer:       String? = null,
  val onSecondaryContainer:     String? = null,

  val tertiary:                 String? = null,
  val onTertiary:               String? = null,
  val tertiaryContainer:        String? = null,
  val onTertiaryContainer:      String? = null,

  val background:               String? = null,
  val onBackground:             String? = null,

  val surface:                  String? = null,
  val onSurface:                String? = null,
  val surfaceVariant:           String? = null,
  val onSurfaceVariant:         String? = null,
  val surfaceTint:              String? = null,
  val inverseSurface:           String? = null,
  val inverseOnSurface:         String? = null,
  val surfaceBright:            String? = null,
  val surfaceDim:               String? = null,
  val surfaceContainer:         String? = null,
  val surfaceContainerHigh:     String? = null,
  val surfaceContainerHighest:  String? = null,
  val surfaceContainerLow:      String? = null,
  val surfaceContainerLowest:   String? = null,

  val outline:                  String? = null,
  val outlineVariant:           String? = null,
  val scrim:                    String? = null,

  val error:                    String? = null,
  val onError:                  String? = null,
  val errorContainer:           String? = null,
  val onErrorContainer:         String? = null
) {

  /* ======= Parse Data */
  /* === Return a color or its fallback if null */
  fun String?.toColorOr(fallback: Color) = this?.let { Color(parseColor(it)) } ?: fallback

  /* === Turn ThemeData into ColorScheme */
  fun toColorScheme(): ColorScheme {

    /* --- Get theme base */
    val base = if (baseSchemeDark) darkColorScheme() else lightColorScheme()

    return ColorScheme(
      primary                 = primary.toColorOr(base.primary),
      onPrimary               = onPrimary.toColorOr(base.onPrimary),
      primaryContainer        = primaryContainer.toColorOr(base.primaryContainer),
      onPrimaryContainer      = onPrimaryContainer.toColorOr(base.onPrimaryContainer),
      inversePrimary          = inversePrimary.toColorOr(base.inversePrimary),

      secondary               = secondary.toColorOr(base.secondary),
      onSecondary             = onSecondary.toColorOr(base.onSecondary),
      secondaryContainer      = secondaryContainer.toColorOr(base.secondaryContainer),
      onSecondaryContainer    = onSecondaryContainer.toColorOr(base.onSecondaryContainer),

      tertiary                = tertiary.toColorOr(base.tertiary),
      onTertiary              = onTertiary.toColorOr(base.onTertiary),
      tertiaryContainer       = tertiaryContainer.toColorOr(base.tertiaryContainer),
      onTertiaryContainer     = onTertiaryContainer.toColorOr(base.onTertiaryContainer),

      background              = background.toColorOr(base.background),
      onBackground            = onBackground.toColorOr(base.onBackground),

      surface                 = surface.toColorOr(base.surface),
      onSurface               = onSurface.toColorOr(base.onSurface),
      surfaceVariant          = surfaceVariant.toColorOr(base.surfaceVariant),
      onSurfaceVariant        = onSurfaceVariant.toColorOr(base.onSurfaceVariant),
      surfaceTint             = surfaceTint.toColorOr(base.surfaceTint),
      inverseSurface          = inverseSurface.toColorOr(base.inverseSurface),
      inverseOnSurface        = inverseOnSurface.toColorOr(base.inverseOnSurface),
      surfaceBright           = surfaceBright.toColorOr(base.surfaceBright),
      surfaceDim              = surfaceDim.toColorOr(base.surfaceDim),
      surfaceContainer        = surfaceContainer.toColorOr(base.surfaceContainer),
      surfaceContainerHigh    = surfaceContainerHigh.toColorOr(base.surfaceContainerHigh),
      surfaceContainerHighest = surfaceContainerHighest.toColorOr(base.surfaceContainerHighest),
      surfaceContainerLow     = surfaceContainerLow.toColorOr(base.surfaceContainerLow),
      surfaceContainerLowest  = surfaceContainerLowest.toColorOr(base.surfaceContainerLowest),

      outline                 = outline.toColorOr(base.outline),
      outlineVariant          = outlineVariant.toColorOr(base.outlineVariant),
      scrim                   = scrim.toColorOr(base.scrim),

      error                   = error.toColorOr(base.error),
      onError                 = onError.toColorOr(base.onError),
      errorContainer          = errorContainer.toColorOr(base.errorContainer),
      onErrorContainer        = onErrorContainer.toColorOr(base.onErrorContainer)
    )
  }
}