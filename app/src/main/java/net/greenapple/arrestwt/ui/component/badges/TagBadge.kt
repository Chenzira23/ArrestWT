/* Author: Green Apple
 * Date Created: August 11, 2025
 * 
 * Other Dates:
 * > Refactored: August 15, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.component.badges

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.type.TagData
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.appearance.ShapeAppearance
import net.greenapple.arrestwt.ui.component.badges.TextBadge
import net.greenapple.arrestwt.util.data.getImage
import net.greenapple.arrestwt.util.data.getMaterialIcon
import net.greenapple.arrestwt.util.paths.asTagImageName
import net.greenapple.arrestwt.util.paths.inTagImages
import android.util.Log
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.QuestionMark

// ====== HELPER FUNCTIONS ======
/* ====== Return Modifier for Passed Shape */
private fun getShapeModifier(
  shape: String? = null
): Modifier {
  return when (shape) {
    "circle"    -> Modifier
      .clip(ShapeAppearance.circle)

    "rectangle" -> Modifier
      .aspectRatio(ShapeAppearance.cardRatio)
      .clip(ShapeAppearance.rectangle)

    "square"    -> Modifier
      .clip(ShapeAppearance.rectangle)

    else        -> getShapeModifier("circle")
  }
}

// ====== TAG BADGE ======
@Composable
fun TagBadge(
  tag:      TagData,
  hidden:   Boolean   = false,
  size:     Dp        = TextAppearance.iconLabel,
  modifier: Modifier  = Modifier
) {

  val effectiveModifier = if (tag.icon != null) modifier.size(size) else Modifier

  val id:           String = if (hidden) "hidden" else tag.id
  val description:  String = "$id tag badge"

  /* ====== Tag UI */
  Box(
    modifier = effectiveModifier
  ) {

    /* === Hidden Icon Badge UI */
    if (hidden) {
      Icon(
        imageVector         = Icons.Filled.QuestionMark,
        tint                = ColorAppearance.onSurfaceColor,
        contentDescription  = description,
        modifier            = modifier
          .fillMaxSize()
          .padding(4.dp)
          .then(getShapeModifier())
      )

    /* === Text Badge UI */
    } else if (tag.icon == null) {
      TextBadge(
        text        = tag.name ?: tag.id,
        badgeColor  = tag.effectiveColor
      )

    /* === Image Badge */
    } else if (tag.hasImage) {

      /* --- Get image or null */
      val image: ImageBitmap? = tag.id.asTagImageName().inTagImages()?.getImage(LocalContext.current)

      /* --- Image UI */
      if (image != null) {
        Image(
          bitmap              = image,
          contentDescription  = description,
          contentScale        = ContentScale.Crop,
          modifier            = modifier
            .fillMaxSize()
            .then(getShapeModifier(tag.iconShape))
        )

      /* --- Backup icon UI */
      } else {
        Icon(
          imageVector         = Icons.Filled.Bookmark,
          tint                = ColorAppearance.onSurfaceColor,
          contentDescription  = description,
          modifier            = modifier
            .fillMaxSize()
            .padding(4.dp)
            .then(getShapeModifier(tag.iconShape))
        )
      }

    /* === Icon Badge UI */
    } else {
      Surface(  
        color = tag.effectiveColor,
        tonalElevation = 2.dp,
        modifier        = Modifier
          .then(getShapeModifier(tag.iconShape))
      ) {
        Icon(
          imageVector         = tag.icon.getMaterialIcon(),
          tint                = ColorAppearance.onSurfaceColor,
          contentDescription  = description,
          modifier            = modifier
              .fillMaxSize()
              .padding(4.dp)
        )
      }
    }
  }
}