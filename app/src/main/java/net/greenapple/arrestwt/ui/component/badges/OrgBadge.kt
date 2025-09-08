/* Author: Green Apple
 * Date Created: August 15, 2025
 *  
 * Notes: 
 */

package net.greenapple.arrestwt.ui.component.badges

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.type.OrgData
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.ShapeAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.util.data.getImage
import net.greenapple.arrestwt.util.paths.asOrgImageName
import net.greenapple.arrestwt.util.paths.inOrgImages
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import kotlin.text.lowercase

// ====== ORG BADGE ======
@Composable
fun OrgBadge(
  org:      OrgData?,
  hidden:   Boolean             = false,
  size:     Dp                  = TextAppearance.iconLabel,
  modifier: Modifier            = Modifier
) {

  val effectiveModifier = modifier
    .size(size)
    .clip(ShapeAppearance.smallRectangle)

  val id:           String = if (hidden || org == null ) "unknown" else org.id
  val description:  String = "$id org badge"

  /* ====== Badge UI */
  Box(
    modifier = effectiveModifier
  ) {

    /* === Null Org Icon Badge UI */
    if (org == null) {
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
    } else if (org.hasImage && org.icon != null) {

      /* --- Get image or null */
      val image: ImageBitmap? = org.id.asOrgImageName().inOrgImages()?.getImage(LocalContext.current)

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
        imageVector         = Icons.Filled.Store,
        tint                = ColorAppearance.onSurfaceColor,
        contentDescription  = description,
        modifier            = Modifier
          .fillMaxSize()
      )
    }
  }
}