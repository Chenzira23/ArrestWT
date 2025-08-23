/* Author: Green Apple
 * Date Created: August 12, 2025
 * 
 * Other Dates:
 * > Refactored: August 15, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.component.badges

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.ShapeAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material.icons.outlined.ArrowCircleLeft
import androidx.compose.material.icons.outlined.ChangeCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ====== HELPER FUNCTIONS ======
/* ====== Make Operation Icon with Passed Information */
@Composable
private fun createOperationBadge(
  icon:         Pair<ImageVector, Color>,
  description:  String,
  modifier:     Modifier
) {

  Icon(
    imageVector         = icon.first,
    contentDescription  = description,
    tint                = icon.second,
    modifier            = modifier
  )
}

/* ====== Determine Icon and Color for Operation */
@Composable
private fun determineIcon(
  operation:  String,
  hidden:     Boolean
): Pair<ImageVector, Pair<Color, Color>> {

  if (hidden) return Pair(Icons.Outlined.Circle, ColorAppearance.flat)
  else return when (operation) {
    "deposit"   -> Pair(Icons.Outlined.ArrowCircleRight, ColorAppearance.positive)
    "withdraw"  -> Pair(Icons.Outlined.ArrowCircleLeft, ColorAppearance.negative)
    "buy"       -> Pair(Icons.Outlined.ArrowCircleRight, ColorAppearance.negative)
    "sell"      -> Pair(Icons.Outlined.ArrowCircleLeft, ColorAppearance.positive)
    "get"       -> Pair(Icons.Outlined.ChangeCircle, ColorAppearance.positive)
    "give"      -> Pair(Icons.Outlined.ChangeCircle, ColorAppearance.negative)
    else        -> Pair(Icons.Outlined.Circle, ColorAppearance.flat)
  }
}

// ====== OPERATION BADGE UI ======
@Composable
fun OperationBadge(
  operation:  String    = "unknown",
  hidden:     Boolean   = false,
  size:       Dp        = TextAppearance.iconLabel,
  modifier:   Modifier  = Modifier
) {

  val effectiveModifier: Modifier = modifier
    .size(size)

  /* --- Determine the icon and its color. */
  val icon: Pair<ImageVector, Pair<Color, Color>> = determineIcon(operation, hidden)

  /* === Create the Badge UI on Circular Surface */
  Surface(
    color     = icon.second.second,
    shape     = ShapeAppearance.circle,
    modifier  = effectiveModifier
  ) {

    createOperationBadge(
      icon        = Pair(icon.first, icon.second.first),
      description = "$operation operation badge",
      modifier    = Modifier
        .fillMaxSize()
        .padding(1.dp)
    )
  }
}