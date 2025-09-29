/* Author: Green Apple
 * Date Created: August 19, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.page

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.NavRoute
import net.greenapple.arrestwt.ui.appearance.LayoutAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.BackButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

// ====== EDUCATE PAGE ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducatePage(
  onBack: () -> Unit = {}
) {

  /* ====== Start UI */
  Column(
    modifier = LayoutAppearance.primaryContainerModifier
  ) {

    /* === Top Bar */
    CenterAlignedTopAppBar(

      /* --- Back button */
      navigationIcon = { BackButton(onBack = onBack) },

      /* --- Add Educate page title */
      title = {
        Text(
          text  = NavRoute.Educate.label,
          style = TextAppearance.title
        )
      }
    )

    LazyColumn() {
    }
  }
}