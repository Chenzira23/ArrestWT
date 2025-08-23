/* Author: Green Apple
 * Date Created: August 14, 2025
 * 
 * Note: 
 */

package net.greenapple.arrestwt.ui.appearance

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.shapes.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.geometry.Size

// ====== SHAPES STYLE ======
class ShapeAppearance {
  companion object {

    /* ====== Standard Shapes */
    val circle:           Shape = CircleShape
    val oval:             Shape = RoundedCornerShape(50)
    val diamond:          Shape = IndentedShape(0.5)
    val rectangle:        Shape = RoundedCornerShape(10)
    val smallRectangle:   Shape = RoundedCornerShape(30)

    /* ====== Aspect Ratios */
    val cardRatio:        Float = 43f / 27f

    /* ====== Material Shapes */
    val primaryContainer: RoundedCornerShape = RoundedCornerShape(16.dp)
  }
}