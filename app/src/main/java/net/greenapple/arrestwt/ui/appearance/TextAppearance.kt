/* Author: Green Apple
 * Date Created: August 4, 2025
 * 
 * Other Dates:
 * > Refactored: August 15, 2025
 * 
 * Note: Values for text styles assigned to components.
 */

package net.greenapple.arrestwt.ui.appearance

// ====== IMPORTS ======
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme.typography

// ====== TEXT STYLE ======
class TextAppearance {
  companion object {

    /* ====== Font Sizes */
    val displayLargeSize:   TextUnit = 57.sp
    val displayMediumSize:  TextUnit = 45.sp
    val displaySmallSize:   TextUnit = 36.sp
    val headingLargeSize:   TextUnit = 32.sp
    val headingMediumSize:  TextUnit = 28.sp
    val headingSmallSize:   TextUnit = 24.sp
    val titleLargeSize:     TextUnit = 22.sp
    val titleMediumSize:    TextUnit = 16.sp
    val titleSmallSize:     TextUnit = 14.sp
    val bodyLargeSize:      TextUnit = 16.sp
    val bodyMediumSize:     TextUnit = 14.sp
    val bodySmallSize:      TextUnit = 12.sp
    val labelLargeSize:     TextUnit = 14.sp
    val labelMediumSize:    TextUnit = 12.sp
    val labelSmallSize:     TextUnit = 11.sp

    val titleSize:      TextUnit  = titleLargeSize
    val headingSize:    TextUnit  = titleMediumSize
    val subheadingSize: TextUnit  = titleSmallSize
    val labelSize:      TextUnit  = labelMediumSize
    val displaySize:    TextUnit  = displaySmallSize
    val bodySize:       TextUnit  = bodyMediumSize

    /* ====== Icon Sizes */
    val iconLabel:    Dp  = labelSize.value.dp + 12.dp
    val iconDisplay:  Dp  = 140.dp
    val iconLarge:    Dp  = 40.dp
    val iconSmall:    Dp  = 20.dp

    /* ====== Line Heights */
    val displayLargeHeight:   TextUnit = 64.sp
    val displayMediumHeight:  TextUnit = 52.sp
    val displaySmallHeight:   TextUnit = 44.sp
    val headingLargeHeight:   TextUnit = 40.sp
    val headingMediumHeight:  TextUnit = 36.sp
    val headingSmallHeight:   TextUnit = 32.sp
    val titleLargeHeight:     TextUnit = 28.sp
    val titleMediumHeight:    TextUnit = 24.sp
    val titleSmallHeight:     TextUnit = 20.sp
    val bodyLargeHeight:      TextUnit = 24.sp
    val bodyMediumHeight:     TextUnit = 20.sp
    val bodySmallHeight:      TextUnit = 16.sp
    val labelLargeHeight:     TextUnit = 20.sp
    val labelMediumHeight:    TextUnit = 16.sp
    val labelSmallHeight:     TextUnit = 16.sp

    val titleHeight:      TextUnit  = titleLargeHeight
    val headingHeight:    TextUnit  = titleMediumHeight
    val subheadingHeight: TextUnit  = titleSmallHeight
    val labelHeight:      TextUnit  = labelMediumHeight
    val displayHeight:    TextUnit  = displaySmallHeight
    val bodyHeight:       TextUnit  = bodyMediumHeight

    /* ====== Letter Spacings */
    val displayLargeSpacing:   TextUnit = (-0.25).sp
    val displayMediumSpacing:  TextUnit = 0.sp
    val displaySmallSpacing:   TextUnit = 0.sp
    val headingLargeSpacing:   TextUnit = 0.sp
    val headingMediumSpacing:  TextUnit = 0.sp
    val headingSmallSpacing:   TextUnit = 0.sp
    val titleLargeSpacing:     TextUnit = 0.sp
    val titleMediumSpacing:    TextUnit = 0.15.sp
    val titleSmallSpacing:     TextUnit = 0.1.sp
    val bodyLargeSpacing:      TextUnit = 0.5.sp
    val bodyMediumSpacing:     TextUnit = 0.25.sp
    val bodySmallSpacing:      TextUnit = 0.4.sp
    val labelLargeSpacing:     TextUnit = 0.1.sp
    val labelMediumSpacing:    TextUnit = 0.5.sp
    val labelSmallSpacing:     TextUnit = 0.5.sp

    val titleSpacing:       TextUnit  = titleLargeSpacing
    val headingSpacing:     TextUnit  = titleMediumSpacing
    val subheadingSpacing:  TextUnit  = titleSmallSpacing
    val labelSpacing:       TextUnit  = labelMediumSpacing
    val displaySpacing:     TextUnit  = displaySmallSpacing
    val bodySpacing:        TextUnit  = bodyMediumSpacing

    /* ====== Font Styles */
    val title: TextStyle = TextStyle(
      fontWeight  = FontWeight.Bold,
      fontSize    = titleSize,
      lineHeight  = titleHeight
    )

    val heading: TextStyle = TextStyle(
      fontWeight  = FontWeight.Normal,
      fontSize    = headingSize,
      lineHeight  = headingHeight
    )

    val headingLarge: TextStyle = TextStyle(
      fontWeight  = FontWeight.Bold,
      fontSize    = titleMediumSize,
      lineHeight  = titleMediumHeight
    )

    val subheading: TextStyle = TextStyle(
      fontWeight  = FontWeight.Normal,
      fontSize    = subheadingSize,
      lineHeight  = subheadingHeight
    )

    val label: TextStyle = TextStyle(
      fontWeight  = FontWeight.Bold,
      fontSize    = labelSize,
      lineHeight  = labelHeight
    )

    val display: TextStyle = TextStyle(
      fontWeight  = FontWeight.Bold,
      fontSize    = displaySize,
      lineHeight  = displayHeight
    )

    val body: TextStyle = TextStyle(
      fontWeight  = FontWeight.Normal,
      fontSize    = bodySize,
      lineHeight  = bodyHeight
    )

    val valueLarge: TextStyle = TextStyle(
      fontFamily  = FontFamily.Monospace,
      fontWeight  = FontWeight.Bold,
      fontSize    = headingMediumSize,
      lineHeight  = headingMediumHeight
    )

    val valueMedium: TextStyle = TextStyle(
      fontFamily  = FontFamily.Monospace,
      fontWeight  = FontWeight.Bold,
      fontSize    = titleMediumSize,
      lineHeight  = titleMediumHeight
    )

    val valueSmall: TextStyle = TextStyle(
      fontFamily  = FontFamily.Monospace,
      fontWeight  = FontWeight.Bold,
      fontSize    = labelSize,
      lineHeight  = labelHeight
    )
  }  
}