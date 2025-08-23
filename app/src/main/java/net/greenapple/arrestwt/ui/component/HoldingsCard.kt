/* Author: Green Apple
 * Date Created: August 5, 2025
 * 
 * Other Dates:
 * > Refactored: August 15, 2025
 * 
 * Note:
 */

package net.greenapple.arrestwt.ui.component

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.LayoutAppearance
import net.greenapple.arrestwt.ui.appearance.ShapeAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.badges.TextBadge
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.Alignment

// ====== HOLDINGS CARD COMPONENT ======
@Composable
fun HoldingsCard(
  title:        String,
  fundsValue:   String,
  fundsHidden:  Boolean             = false,
  badgeText:    String,
  colors:       CardColors?         = null,
  modifier:     Modifier?           = null
) {

  val effectiveColors: CardColors = colors
    ?: CardDefaults.cardColors(
      containerColor = ColorAppearance.containerColor
    )

  val effectiveModifier: Modifier = modifier
    ?: LayoutAppearance.primaryContainerModifier

  /* ====== CARD UI ====== */
  Card(
    shape     = ShapeAppearance.primaryContainer,
    colors    = effectiveColors,
    modifier  = effectiveModifier
  ) {

    /* === Within Card Column */
    Column(
      horizontalAlignment = Alignment.Start,
      modifier            = LayoutAppearance.withinContainerCol
    ) {
      
      /* --- Card title */
      Text(
        text  = title,
        style = TextAppearance.heading,
        color = ColorAppearance.covertColor
      )

      /* --- Add line below title */
      HorizontalDivider(
        thickness = 2.dp,
        color     = ColorAppearance.covertColor,
        modifier  = Modifier.fillMaxWidth()
      )

      /* --- Funds value string */
      Text(
        text      = if (fundsHidden) "$••,•••.••" else fundsValue,
        style     = TextAppearance.valueLarge
      )

      /* --- Add percent change badge */
      TextBadge(
        text        = if (fundsHidden) "••.••%" else badgeText,
        textColor   = if (fundsHidden) ColorAppearance.outlineColor else ColorAppearance.negativeForeground,
        textStyle   = TextAppearance.valueSmall,
        badgeColor  = if (fundsHidden) ColorAppearance.covertColor else ColorAppearance.negativeBackground
      )
    }
  }
}