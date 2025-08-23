/* Author: Green Apple
 * Date Created: August 5, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.page

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.NavRoute
import net.greenapple.arrestwt.ui.component.ButtonSetting
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.viewmodel.SettingsViewModel
import net.greenapple.arrestwt.ui.component.DropdownSetting
import net.greenapple.arrestwt.ui.component.HiddenTextSetting
import net.greenapple.arrestwt.ui.component.BackButton
import net.greenapple.arrestwt.util.ThemeUtils
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

// ====== SETTINGS PAGE ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
  settingsViewModel:  SettingsViewModel,
  onBack:             () -> Unit = {}
) {
  
  /* --- Get current local context */
  val context = LocalContext.current

  /* ====== Get Settings Values */
  /* === Theme Values */
  val themeMap                  = remember { ThemeUtils.listDefaultJsonThemes(context) }
  val themeOptions              = listOf("Light", "Dark") + themeMap.keys.toList()
  val selectedTheme             by settingsViewModel.selectedThemeFlow.collectAsState()

  /* === Page Values */
  val startingPageLabels        = remember { NavRoute.bottomNavRoutes.map { it.label } }
  val startingPageLabelsByRoute = remember { NavRoute.bottomNavRoutes.associate { it.label to it.route } }
  val startingPageRoutesByLabel = remember { NavRoute.bottomNavRoutes.associate { it.route to it.label } }
  val selectedStartingPage      by settingsViewModel.selectedStartingPageFlow.collectAsState()

  val startingPageSafePage      = remember(selectedStartingPage) {
    if (startingPageLabelsByRoute.containsKey(selectedStartingPage)) selectedStartingPage
    else NavRoute.bottomNavRoutes.first().route
  }

  /* === API Values */
  val hasApiKeyFmp              by settingsViewModel.hasApiKeyFmpFlow.collectAsState()
  val hasApiKeyAv               by settingsViewModel.hasApiKeyAvFlow.collectAsState()

  /* === LazyList Values */
  val lazyListState = rememberLazyListState()

  /* ====== Start UI */
  Column() {
    
    /* === Top Bar */
    CenterAlignedTopAppBar(

      /* --- Back button */
      navigationIcon = { BackButton(onBack = onBack) },

      /* --- Settings page title */
      title = {
        Text(
          text  = NavRoute.Settings.label,
          style = TextAppearance.title
        )
      }
    )

    /* === Settings List */
    LazyColumn(
      state = lazyListState,
      verticalArrangement = Arrangement.spacedBy(24.dp),
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {

      /* --- Appearance section */
      item {
        Column() {

          /* Appearance heading */
          Text(
            text  = "Appearance",
            style = TextAppearance.heading
          )

          /* Theme dropdown selector */
          DropdownSetting(
            settingName       = "Theme",
            options           = themeOptions,
            selectedOption    = selectedTheme,
            onOptionSelected  = { settingsViewModel.setSelectedTheme(it) }
          )

          /* Theme dropdown selector */
          DropdownSetting(
            settingName       = "Starting Page",
            options           = startingPageLabels,
            selectedOption    = remember(startingPageSafePage) { startingPageLabelsByRoute[startingPageSafePage] ?: startingPageLabels.first() },
            onOptionSelected  = {
              val route = startingPageRoutesByLabel[it] ?: NavRoute.bottomNavRoutes.first().route
              settingsViewModel.setSelectedStartingPage(route)
            }
          )
        }
      }

      /* --- APIs section */
      item {
        Column() {

          /* APIs heading */
          Text(
            text  = "APIs",
            style = TextAppearance.heading
          )

          /* Financial modeling prep API key hiddent text input */
          HiddenTextSetting(
            settingName   = "Alpha Vantage Key",
            loadValue     = { settingsViewModel.loadApiKeyAv() },
            onValueChange = { settingsViewModel.onApiKeyAvEntered(it) },
            buttonText    = if (hasApiKeyAv) "Edit" else "Set"
          )

          /* Alpha vantage API key hiddent text input */
          HiddenTextSetting(
            settingName   = "Financial Modeling Prep Key",
            loadValue     = { settingsViewModel.loadApiKeyFmp() },
            onValueChange = { settingsViewModel.onApiKeyFmpEntered(it) },
            buttonText    = if (hasApiKeyFmp) "Edit" else "Set"
          )
        }
      }

      /* --- Search section */
      item {
        Column() {

          /* Search heading */
          Text(
            text  = "Search",
            style = TextAppearance.heading
          )

          /* Manually check for NASDAQ stock list update */
          ButtonSetting(
            settingName = "Update NASDAQ Stock List",
            buttonName = "Update",
            onClick = {}
          )

          /* Manually check for NYSE stock list update */
          ButtonSetting(
            settingName = "Update NYSE Stock List",
            buttonName = "Update",
            onClick = {}
          )

          /* Manually check for CSE stock list update */
          ButtonSetting(
            settingName = "Update CSE Stock List",
            buttonName = "Update",
            onClick = {}
          )
        }
      }
    }
  }
}