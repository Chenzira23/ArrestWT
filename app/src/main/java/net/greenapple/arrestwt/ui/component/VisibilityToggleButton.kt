/* Author: Green Apple
 * Date Created: August 12, 2025
 * 
 * Note: Code for the home page and its information
 */

package net.greenapple.arrestwt.ui.component

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.viewmodel.VisibilityViewModel
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

// ====== VISIBILITY TOGGLE BUTTON COMPONENT ======
@Composable
fun VisibilityToggleButton(
  visibilityViewModel:  VisibilityViewModel,
  hidden:               Boolean
) {
  IconButton(
    onClick = { visibilityViewModel.setValuesHidden(!hidden) }
  ) {

    /* --- Icon when funds are hidden */
    if (hidden) {
      Icon(
        imageVector         = Icons.Filled.Visibility,
        contentDescription  = "show funds"
      )

    /* --- Icon when funds are visible */
    } else {
      Icon(
        imageVector         = Icons.Filled.VisibilityOff,
        contentDescription  = "hide funds"
      )
    }
  }
}