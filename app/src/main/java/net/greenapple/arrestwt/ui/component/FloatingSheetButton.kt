/* Author: Green Apple
 * Date Created: August 10, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.component

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.LayoutAppearance
import net.greenapple.arrestwt.ui.appearance.ShapeAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import androidx.compose.runtime.Composable
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.draw.rotate
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape

// ====== FLOATING SHEET BUTTON ENTRY ======
data class SheetButtonEntry(
  val title:    String      = "Unnamed Entry",
  val icon:     ImageVector = Icons.Default.QuestionMark,
  val onClick:  () -> Unit  = {}
)

// ====== FLOATING SHEET BUTTON ENTRY UI ======
@Composable
private fun SheetButtonItem(
  action: SheetButtonEntry
) {

  /* ====== Side Component UI */
  Row(
    verticalAlignment = Alignment.CenterVertically
  ) {

    /* === Entry Label UI */
    Surface(
      color           = ColorAppearance.sheetColor,
      contentColor    = ColorAppearance.onSurfaceColor,
      shadowElevation = LayoutAppearance.lesserSecondaryPadding,
      shape           = ShapeAppearance.rectangle,
      modifier        = Modifier
        .clickable(onClick = action.onClick)
    ) {

      /* === Entry Label Text */
      Text(
        text      = action.title,
        style     = TextAppearance.label,
        modifier  = Modifier
        .padding(
          horizontal  = LayoutAppearance.primaryPadding,
          vertical    = LayoutAppearance.secondaryPadding
        )
      )
    }

    /* --- Spacer between entry label and icon */
    Spacer(
      modifier = Modifier
        .width(12.dp)
    )

    /* === Entry Button UI */
    SmallFloatingActionButton(
      containerColor  = ColorAppearance.containerColor,
      onClick         = action.onClick
    ) {
      
      /* === Entry Icon */
      Icon(
        imageVector         = action.icon,
        contentDescription  = action.title,
        modifier            = Modifier
          .size(LayoutAppearance.mediumSize)
      )
    }
  }
}

// ====== FLOATING SHEET BUTTON COMPONENT ======
@Composable
fun FloatingSheetButton(
  icon:     ImageVector,
  rotate:   Boolean                 = false,
  actions:  List<SheetButtonEntry>,
  modifier: Modifier                = Modifier
) {

  /* --- Button Values and Variables */
  var expanded by remember { mutableStateOf(false) }
  val rotation by animateFloatAsState(
    if (expanded) 45f else 0f
  )
  
  /* --- If the button is expanded, show popup UI */
  if (expanded) {

    /* ====== Popup UI */
    Box(
      modifier = Modifier
        .fillMaxSize()
    ) {

      val arrangement = Arrangement.spacedBy(LayoutAppearance.lesserPrimaryPadding)

      /* ====== Sheet UI */
      Column(
        verticalArrangement = arrangement,
        horizontalAlignment = Alignment.End,
        modifier            = modifier
          .navigationBarsPadding()
      ) {

        /* --- Animation for popup */
        AnimatedVisibility(
          visible = true,
          enter   = fadeIn()  + expandVertically(expandFrom     = Alignment.Bottom),
          exit    = fadeOut() + shrinkVertically(shrinkTowards  = Alignment.Bottom)
        ) {

          /* === Sheet UI */
          Column(
            verticalArrangement = arrangement,
            horizontalAlignment = Alignment.End
          ) {

            /* --- Sheet entries */
            actions.forEach { action ->
              SheetButtonItem(
                action.copy(
                  onClick = {
                    expanded = false
                    action.onClick()
                  } 
                )
              )
            }
          }
        }

        /* === Opened Button UI */
        FloatingActionButton(
          containerColor  = ColorAppearance.containerColor,
          onClick         = { expanded = false }
        ) {

          /* --- Button Icon */
          Icon(
            imageVector         = icon,
            contentDescription  = "close",
            modifier            = if (rotate) Modifier.rotate(rotation) else Modifier
          )
        }
      }
    }

  /* --- If the button is not expanded, do no show popup UI */
  } else {
    FloatingActionButton(
      containerColor  = ColorAppearance.containerColor.copy(alpha = 0.5f),
      onClick         = { expanded = true },
      modifier        = modifier
        .navigationBarsPadding()
    ) {

      /* --- Button Icon */
      Icon(
        imageVector         = icon,
        contentDescription  = "expand",
        modifier            = if (rotate) Modifier.rotate(rotation) else Modifier
      )
    }
  }
}