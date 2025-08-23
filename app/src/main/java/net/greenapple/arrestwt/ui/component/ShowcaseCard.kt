/* Author: Green Apple
 * Date Created: August 22, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.component

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.LayoutAppearance
import net.greenapple.arrestwt.ui.appearance.ShapeAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.background
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.clip

// ====== SHOWCASE CARD ENTRY ======
data class ShowcaseCardEntry(
  val display:  Any,
  val details:  String      = "",
  val onClick:  () -> Unit  = {}
)

// ====== SHOWCASE CARD COMPONENT ======
@Composable
fun ShowcaseCard(
  title:    String                    = "List",
  items:    List<ShowcaseCardEntry>?  = null,
  hidden:   Boolean                   = false,
  colors:   CardColors?               = null,
  modifier: Modifier?                 = null
) {

  /* --- Card values */
  val effectiveColors: CardColors = colors
    ?: CardDefaults.cardColors(
      containerColor = ColorAppearance.containerColor
    )

  val effectiveModifier: Modifier = modifier
    ?: LayoutAppearance.primaryContainerModifier

  /* --- Card variables */
  var index by remember { mutableStateOf(0) }

  /* ====== Card UI */
  Card(
    shape     = ShapeAppearance.primaryContainer,
    colors    = effectiveColors,
    modifier  = effectiveModifier
  ) {

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier            = LayoutAppearance.withinContainerCol
    ) {

      /* === Title  */
      Text(
        text  = title,
        style = TextAppearance.headingLarge
      )

      /* --- Add line below title */
      HorizontalDivider(
        thickness = 2.dp,
        color     = ColorAppearance.covertColor,
        modifier  = Modifier
          .fillMaxWidth()
          .padding(vertical = 6.dp)
      )

      Box(
        contentAlignment  = Alignment.Center,
        modifier          = Modifier
          .height(TextAppearance.iconDisplay)
          .clipToBounds()
      ) {
        /* === Display and List Navigation  */
        Row(
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment     = Alignment.CenterVertically,
          modifier              = Modifier
            .fillMaxSize()
        ) {

          if (items != null) {

            /* --- Move to last entry button */
            IconButton(
              onClick = {
                if (index <= 0) index = ((items?.size) ?: 1) - 1
                else index--
              }
            ) {
              Icon(
                imageVector         = Icons.Filled.ArrowCircleLeft,
                contentDescription  = "last entry",
                modifier            = Modifier
                  .padding(horizontal = 6.dp)
              )
            }   

            /* --- Item display */
            Surface(
              color     = ColorAppearance.flatBackground,
              onClick   = items[index].onClick,
              modifier  = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 8.dp)
                .clip(ShapeAppearance.rectangle)
            ) {
              
              /* If the item is an image */
              if (items[index].display is ImageBitmap) {
                Image(
                  bitmap              = items[index].display as ImageBitmap,
                  contentDescription  = "$title index $index image",
                  contentScale        = ContentScale.Crop
                )
                  
              /* If the item is an icon */
              } else if (items[index].display is ImageVector) {
                Icon(
                  imageVector         = items[index].display as ImageVector,
                  contentDescription  = "$title index $index icon",
                  modifier            = Modifier
                    .fillMaxSize()
                )

              /* If the item is not a correct type */
              } else {
                Text(
                  text  = "Icon/Image Error",
                  style = TextAppearance.display
                )
              }
            }

            /* --- Move to next entry button */
            IconButton(
              onClick = {
                if (index >= items.size - 1) index = 0
                else index++
              }
            ) {
              Icon(
                imageVector         = Icons.Filled.ArrowCircleRight,
                contentDescription  = "next entry"
              )
            }

          } else {
            Text(
              text  = "Nothing to display",
              style = TextAppearance.display
            )
          }
        }
      }

      /* === Detials Text */
      if (items != null) {
        Text(
          text      = items[index].details,
          style     = TextAppearance.body,
          color     = ColorAppearance.covertColor,
          modifier  = Modifier
            .padding(8.dp)
        )
      }
      

      /* === Index  */
      Text(
        text  = "${index + 1} / ${(items?.size ?: 0)}",
        style = TextAppearance.valueMedium,
      )
    }
  }
}