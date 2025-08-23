/* Author: Green Apple
 * Date Created: August 10, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.page

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.NavRoute
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.BackButton
import net.greenapple.arrestwt.ui.component.DefaultPageBox
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column


// ====== ADD TRANSACTION PAGE ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsPage(
  onBack: () -> Unit = {}
) {

  /* ====== Start UI */
  Column() {

    /* === Top Bar */
    CenterAlignedTopAppBar(

      /* --- Back button */
      navigationIcon = { BackButton(onBack = onBack) },

      /* --- Add Account page title */
      title = {
        Text(
          text  = NavRoute.Tags.label,
          style = TextAppearance.title
        )
      }
    )

    /* ====== No Profile Body */
    DefaultPageBox(
      text  = "Tag management coming soon!",
    )
  }
}