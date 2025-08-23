/* Author: Green Apple
 * Date Created: August 12, 2025
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


// ====== ADD TAG PAGE ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTagPage(
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
          text  = NavRoute.AddTag.label,
          style = TextAppearance.title
        )
      }
    )

    /* ====== No Add Tags Body */
    DefaultPageBox(
      text  = "Tag management coming soon!",
    )
  }
}