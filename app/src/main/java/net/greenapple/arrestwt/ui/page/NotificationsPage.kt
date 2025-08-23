/* Author: Green Apple
 * Date Created: August 1, 2025
 * 
 * Note: Code for the notifications page and its information
 */

package net.greenapple.arrestwt.ui.page

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.DefaultPageBox
import net.greenapple.arrestwt.ui.component.BackButton
import net.greenapple.arrestwt.ui.NavRoute
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

// ====== NOTIFICATIONS PAGE ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsPage(
  onBack: () -> Unit = {}
) {

  /* ====== Start UI ======*/
  Column() {

    /* ====== TOP BAR */
    CenterAlignedTopAppBar(
      navigationIcon = { BackButton(onBack = onBack) },
      title = {
        Text(
          text  = NavRoute.Notifications.label,
          style = TextAppearance.title
        )
      }
    )

    /* ====== No Messages Body */
    DefaultPageBox(
      text  = "Come back when you're a little more popular...",
    )
  }
}