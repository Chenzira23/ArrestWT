/* Author: Green Apple
 * Date Created: August 1, 2025
 * 
 * Note: Button component for a SettingsPage listing
 */

package net.greenapple.arrestwt.ui.component.settings

// ====== IMPORTS ======
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.greenapple.arrestwt.ui.appearance.TextAppearance

// ====== BUTTON SETTING COMPONENT ======
@Composable
fun ButtonSetting(
  settingName:  String,
  buttonName:   String,
  onClick:      () -> Unit  = {},
  modifier:     Modifier?   = null
) {

  val effectiveModifier: Modifier = modifier
    ?: Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  
  /* ====== Component UI */
  Row(
    verticalAlignment     = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier              = effectiveModifier
  ) {

    /* === Setting Name UI */
    Text(
      text  = settingName,
      style = TextAppearance.subheading
    )

    /* === Button UI */
    Button(onClick = onClick) {
      Text(
        text  = buttonName,
        style = TextAppearance.label
      )
    }
  }
}