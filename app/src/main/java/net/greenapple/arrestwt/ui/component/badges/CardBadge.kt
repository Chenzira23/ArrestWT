/* Author: Green Apple
 * Date Created: August 10, 2025
 * 
 * Other Dates:
 * > Refactored: August 15, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.component.badges

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.type.CardData
import net.greenapple.arrestwt.ui.appearance.ShapeAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.util.data.getImage
import net.greenapple.arrestwt.util.paths.asCardImageName
import net.greenapple.arrestwt.util.paths.inCardImages
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.CreditCardOff
import androidx.compose.material.icons.filled.QuestionMark
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import android.graphics.BitmapFactory
import android.util.Log
import java.io.IOException

// ====== CARD BADGE ======
@Composable
fun CardBadge(
  card:     CardData?,
  hidden:   Boolean             = false,
  size:     Dp                  = TextAppearance.iconSmall,
  modifier: Modifier            = Modifier
) {

  val name:         String = if (hidden || card == null) "unknown" else card.name.lowercase()
  val description:  String = "$name card badge"

  val effectiveModifier: Modifier = modifier
    .size(size)
    .clip(ShapeAppearance.rectangle)

  /* ====== Badge UI */
  Box(
    modifier = effectiveModifier
  ) {

    /* === Hidden Icon Badge UI */
    if (card == null) {
      Icon(
        imageVector         = Icons.Filled.Warning,
        contentDescription  = description
      )

    } else if (hidden) {
      Icon(
        imageVector         = Icons.Filled.CreditCardOff,
        contentDescription  = description
      )

    /* === Image Badge */
    } else if (card.hasImage) {
      
      /* ====== Get Image or Null */
      val image: ImageBitmap? = card.uuid.asCardImageName().inCardImages().getImage(LocalContext.current)

      /* --- Image UI */
      if (image != null) {
        Image(
          bitmap              = image,
          contentDescription  = description,
          contentScale        = ContentScale.Crop,
          modifier            = Modifier
            .fillMaxSize()
            .aspectRatio(ShapeAppearance.cardRatio)
        )

      /* --- Backup icon UI */
      } else {
        Icon(
          imageVector         = Icons.Filled.QuestionMark,
          contentDescription  = description
        )
      }

    /* === Icon Badge UI */
    } else {
      Icon(
        imageVector         = Icons.Filled.CreditCard,
        contentDescription  = description
      )
    }
  }
}