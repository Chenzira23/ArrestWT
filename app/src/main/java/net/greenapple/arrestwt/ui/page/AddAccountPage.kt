/* Author: Green Apple
 * Date Created: August 10, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.page

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.NavRoute
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.DefaultPageBox
import net.greenapple.arrestwt.ui.component.BackButton
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

// ====== ADD ACCOUNT PAGE ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountPage(
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
          text  = NavRoute.AddAccount.label,
          style = TextAppearance.title
        )
      }
    )

    /* ====== No Profile Body */
    DefaultPageBox(
      text  = "Accounts coming soon!",
    )
  }
}