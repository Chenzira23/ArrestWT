/* Author: Green Apple
 * Date Created: August 14, 2025
 * 
 * Other Dates:
 * > Refactored: August 15, 2025
 * 
 * Note: 
 */

package net.greenapple.arrestwt.ui.appearance

// ====== IMPORTS ======
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

// ====== LAYOUT STYLE ======
class LayoutAppearance {
  companion object {

    /* ====== Padding Values */
    val indentPadding:            Dp  = 24.dp
    val primaryPadding:           Dp  = 16.dp
    val lesserPrimaryPadding:     Dp  = 12.dp
    val secondaryPadding:         Dp  = 8.dp
    val lesserSecondaryPadding:   Dp  = 6.dp

    /* ====== Size Values */
    val mediumSize: Dp  = 20.dp

    /* ====== Modifiers */
    // TODO: Remove
    val primaryContainerModifier: Modifier @Composable get() = Modifier
      .fillMaxWidth()
      .padding(primaryPadding)

    val withinContainerCol:       Modifier @Composable get() = Modifier
      .padding(indentPadding)
      .widthIn(min = 200.dp)
  }
}