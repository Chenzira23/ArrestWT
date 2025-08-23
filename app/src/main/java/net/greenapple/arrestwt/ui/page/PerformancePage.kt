/* Author: Green Apple
 * Date Created: August 1, 2025
 * 
 * Note: Code for the performance page and its information
 */

package net.greenapple.arrestwt.ui.page

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.DefaultPageBox
import net.greenapple.arrestwt.ui.NavRoute
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

// ====== NOTIFICATIONS PAGE ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerformancePage()
{
  /* Start UI */
  Column() {
    /* ====== TOP BAR ====== */
    /* Add top bar */
    CenterAlignedTopAppBar(
      title = {
        Text(
          text  = NavRoute.Performance.label,
          style = TextAppearance.title
        )
      }
    )

    /* ====== No Performance Body */
    DefaultPageBox(
      text  = "Try logging some more transactions to track your performance!",
    )
  }
}