/* Author: Green Apple
 * Date Created: August 13, 2025
 * 
 * Other Dates:
 * > Refactored: August 15, 2025
 * 
 * Note: Values for colors assigned to components.
 */

package net.greenapple.arrestwt.ui.appearance

// ====== IMPORTS ======
import androidx.compose.runtime.Composable
import android.graphics.Color.parseColor
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.graphics.Color

// ====== COLORS ======
class ColorAppearance {
  companion object {

    /* ====== Static Colors */
    val positiveForeground: Color               = Color(parseColor("#88C849"))
    val positiveBackground: Color               = Color(parseColor("#709D4C")).copy(alpha = 0.3f)
    val positive:           Pair<Color, Color>  = Pair(positiveForeground, positiveBackground)

    val negativeForeground: Color               = Color(parseColor("#FE5E51"))
    val negativeBackground: Color               = Color(parseColor("#AC4A43")).copy(alpha = 0.3f)
    val negative:           Pair<Color, Color>  = Pair(negativeForeground, negativeBackground)

    /* ====== Dynamic Colors */
    val flatForeground:     Color               @Composable get() = colorScheme.outline
    val flatBackground:     Color               @Composable get() = colorScheme.outline.copy(alpha = 0.3f)
    val flat:               Pair<Color, Color>  @Composable get() = Pair(flatForeground, flatBackground)

    val backgroundColor:    Color               @Composable get() = colorScheme.background
    val containerColor:     Color               @Composable get() = colorScheme.surfaceVariant
    val sheetColor:         Color               @Composable get() = colorScheme.surface
    val covertColor:        Color               @Composable get() = colorScheme.outline.copy(alpha = 0.6f)
    val onSurfaceColor:     Color               @Composable get() = colorScheme.onSurface
    val outlineColor:       Color               @Composable get() = colorScheme.outline
  }
}