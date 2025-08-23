/* Author: Green Apple
 * Date Created: August 1, 2025
 * 
 * Note: Code for the profile page and its information
 */

package net.greenapple.arrestwt.ui.page

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.DefaultPageBox
import net.greenapple.arrestwt.ui.NavRoute
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

// ====== PROFILE PAGE ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
  onEducateClick: () -> Unit = {}
) {
  /* ====== Start UI */
  Column() {

    /* === Top Bar */
    CenterAlignedTopAppBar(
      title = {
        Text(
          text  = NavRoute.Profile.label,
          style = TextAppearance.title
        )
      },

      actions = {
        /* --- Add learning button */
        IconButton(
          onClick = onEducateClick
        ) {
          Icon(
            imageVector         = NavRoute.Educate.icon,
            contentDescription  = "educate button"
          )
        }
      }
    )

    /* ====== No Profile Body */
    DefaultPageBox(
      text  = "Profile and points coming soon!",
    )
  }
}