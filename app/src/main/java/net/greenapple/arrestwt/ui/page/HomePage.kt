/* Author: Green Apple
 * Date Created: August 1, 2025
 * 
 * Note: Code for the home page and its information
 */

package net.greenapple.arrestwt.ui.page

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.NavRoute
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.appearance.ShapeAppearance
import net.greenapple.arrestwt.ui.appearance.LayoutAppearance
import net.greenapple.arrestwt.ui.component.HoldingsCard
import net.greenapple.arrestwt.ui.component.VisibilityToggleButton
import net.greenapple.arrestwt.ui.component.badges.TextBadge
import net.greenapple.arrestwt.ui.viewmodel.VisibilityViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// ====== HOME PAGE UI ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
  visibilityViewModel:  VisibilityViewModel,
  onNotificationsClick: () -> Unit = {},
  onSettingsClick:      () -> Unit = {}
) {

  /* --- Values for funds categories */
  val hidden          by visibilityViewModel.valuesHiddenFlow.collectAsState()
  val savingsFunds    = remember { 12345.67 }
  val investingFunds  = remember { 12345.67 }
  val totalFunds      = remember { savingsFunds + investingFunds }

  // ====== Start UI
  Column() {

    /* === Top Bar */
    CenterAlignedTopAppBar(
      navigationIcon = {
        Row(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalAlignment     = Alignment.CenterVertically,
          modifier              = Modifier
            .padding(end = 6.dp)
        ) {

          /* --- Notifications button */
          IconButton(
            onClick = onNotificationsClick
          ) {
            Icon(
              NavRoute.Notifications.icon,
              contentDescription = NavRoute.Notifications.label
            )
          }

          /* --- Visibility toggle button */
          VisibilityToggleButton(
            visibilityViewModel = visibilityViewModel,
            hidden              = hidden
          )
        }
      },

      /* --- Home page title */
      title = {
        Text(
          text  = NavRoute.Home.label,
          style = TextAppearance.title
        )
      },

      /* --- Settings button */
      actions = {
        IconButton(
          onClick = onSettingsClick
        ) {
          Icon(
            NavRoute.Settings.icon,
            contentDescription  = NavRoute.Settings.label
          )
        }
      }
    )

    /* === Holdings Cards */
    LazyColumn {

      /* --- Savings funds card */
      item {
        HoldingsCard(
          title       = "TOTAL FUNDS",
          fundsHidden = hidden,
          fundsValue  = "$${"%,.2f".format(totalFunds)}",
          badgeText   = "+ 11.29 all time"
        )
      }

      /* --- Savings funds card */
      item {
        HoldingsCard(
          title       = "SAVINGS",
          fundsHidden = hidden,
          fundsValue  = "$${"%,.2f".format(savingsFunds)}",
          badgeText   = "+ 11.29 all time"
        )
      }

      /* --- Investing funds card */
      item {
        HoldingsCard(
          title       = "INVESTMENTS",
          fundsHidden = hidden,
          fundsValue  = "$${"%,.2f".format(investingFunds)}",
          badgeText   = "+ 11.29 all time"
        )
      }
    }
  }
}