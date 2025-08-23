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
import net.greenapple.arrestwt.data.AssetPaths
import net.greenapple.arrestwt.data.type.PersonData
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.appearance.ShapeAppearance
import net.greenapple.arrestwt.util.data.getImage
import net.greenapple.arrestwt.util.paths.asPersonImageName
import net.greenapple.arrestwt.util.paths.inPersonImages
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOff
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

// ====== PERSON BADGE ======
@Composable
fun PersonBadge(
  person:   PersonData?         = null,
  hidden:   Boolean             = false,
  size:     Dp                  = TextAppearance.iconLabel,
  modifier: Modifier            = Modifier
) {

  val effectiveModifier = modifier
    .size(size)
    .clip(ShapeAppearance.circle)

  val name:         String = if (hidden || person == null) "unknown" else person.name.lowercase()
  val description:  String = "$name person badge"

  /* ====== Badge UI */
  Box(
    modifier = effectiveModifier
  ) {

    /* === Null Person Icon Badge UI */
    if (person == null) {
      Icon(
        imageVector         = Icons.Filled.Warning,
        tint                = ColorAppearance.onSurfaceColor,
        contentDescription  = description,
        modifier            = Modifier
          .fillMaxSize()
      )

    /* === Hidden Icon Badge UI */
    } else if (hidden) {
      Icon(
        imageVector         = Icons.Filled.PersonOff,
        tint                = ColorAppearance.onSurfaceColor,
        contentDescription  = description,
        modifier            = Modifier
          .fillMaxSize()
      )

    /* === Image Badge */
    } else if (person.hasImage) {

      /* --- Get image or null */
      val image: ImageBitmap? = person.uuid.asPersonImageName().inPersonImages().getImage(LocalContext.current)

      /* --- Image UI */
      if (image != null) {
        Image(
          bitmap              = image,
          contentDescription  = description,
          contentScale        = ContentScale.Crop,
          modifier            = Modifier
            .fillMaxSize()
        )

      /* --- Backup icon UI */
      } else {
        Icon(
          imageVector         = Icons.Filled.QuestionMark,
          tint                = ColorAppearance.onSurfaceColor,
          contentDescription  = description,
          modifier            = Modifier
            .fillMaxSize()
        )
      }

    /* === Icon Badge UI */
    } else {
      Icon(
        imageVector         = Icons.Filled.Person,
        tint                = ColorAppearance.onSurfaceColor,
        contentDescription  = description,
        modifier            = Modifier
          .fillMaxSize()
      )
    }
  }
}