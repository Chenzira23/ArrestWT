/* Author: Green Apple
 * Date Created: August 5, 2025
 * 
 * Note:
 */

package net.greenapple.arrestwt.ui.component.badges

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.ShapeAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme

// ====== TEXT BADGE COMPONENT ======
@Composable
fun TextBadge(
  text:       String,
  textColor:  Color?    = null,
  textStyle:  TextStyle = TextAppearance.label,
  badgeColor: Color?    = null,
  modifer:    Modifier  = Modifier

) {

  val effectiveTextColor:   Color = textColor ?: ColorAppearance.onSurfaceColor
  val effectiveBadgeColor:  Color = badgeColor ?: ColorAppearance.flatForeground

  /* ====== Text Badge UI */
  Surface(
    color           = effectiveBadgeColor,
    shape           = ShapeAppearance.oval,
    tonalElevation  = 2.dp,
    modifier        = modifer
  ) {

    /* === Text UI */
    Text(
      text      = text,
      style     = textStyle,
      color     = effectiveTextColor,
      modifier  = Modifier
        .padding(horizontal = 8.dp, vertical = 4.dp)
    )
  }
}