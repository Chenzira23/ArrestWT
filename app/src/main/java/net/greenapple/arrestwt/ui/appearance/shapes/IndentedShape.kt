/* Author: Green Apple
 * Date Created: August 18, 2025
 * 
 * Note: 
 */

package net.greenapple.arrestwt.ui.appearance.shapes

// ====== IMPORTS ======
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sin

// ====== HELPER FUNCTIONS ======
private fun Double.signedPower(power: Double) = sign(this) * abs(this).pow(power)

// ====== SQUIRCLE SHAPE ======
class IndentedShape(
  private val n: Double = 4.0
): Shape {

  /* ====== Constrain Input Value */
  init {
    require(n > 0 && n < 2)  { "Value must be between 0 and 2. Concider using RoundedCornerShape." }
  }

  override fun createOutline(
    size:             Size,
    layoutDirection:  LayoutDirection,
    density:          Density
  ): Outline {

    val width   = size.width
    val height  = size.height
    val a       = width / 2.0
    val b       = height / 2.0

    val path  = Path()
    val steps = 320

    for (i in 0..steps) {
      val t = 2.0 * Math.PI * i / steps
      val x = a + (a * cos(t).signedPower(2.0 / n))
      val y = b + (b * sin(t).signedPower(2.0 / n))

      if (i == 0) path.moveTo(x.toFloat(), y.toFloat())
      else path.lineTo(x.toFloat(), y.toFloat())
    }

    path.close()
    return Outline.Generic(path)
  }
}