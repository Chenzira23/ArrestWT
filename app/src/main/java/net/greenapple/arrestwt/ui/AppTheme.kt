/* Author: Green Apple
 * Date Created: August 5, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.viewmodel.SettingsViewModel
import net.greenapple.arrestwt.util.ThemeUtils
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.Typography
import androidx.compose.material3.MaterialTheme

// ====== APP THEME ======
@Composable
fun AppTheme(
  settingsViewModel:  SettingsViewModel,
  content:            @Composable () -> Unit
) {

  /* --- Get current local context */
  val context       = LocalContext.current

  /* --- Get selected theme flow */
  val selectedTheme by settingsViewModel.selectedThemeFlow.collectAsState()

  /* --- Load theme names mapped to files */
  val themeMap      = remember { ThemeUtils.listDefaultJsonThemes(context) }

  val colorScheme   = remember(selectedTheme) {

    /* Apply theme based on selection */
    when (selectedTheme) {
      "Light" -> lightColorScheme()
      "Dark"  -> darkColorScheme()
      else    -> {

        /* Try to load thems from file or use dark if failed */
        try {
          val fileName = themeMap[selectedTheme]
          fileName?.let {
            ThemeUtils
              .loadThemeFromJson(context, it)
              ?.toColorScheme()
          } ?: darkColorScheme()

        } catch (e: Exception) {
          darkColorScheme()
        }
      }
    }
  }

  /* Apply to material theme for app */
  val typography = Typography()
  MaterialTheme(
    colorScheme = colorScheme,
    typography  = typography,
    content     = content
  )
}