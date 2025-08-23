/* Author: Green Apple
 * Date Created: August 23, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.component.settings

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.ShapeAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// ====== TEXT SETTING COMPONENT ======
@Composable
fun TextSetting(
  value:          String,
  settingName:    String,
  singleLine:     Boolean           = true,
  onValueChange:  (String) -> Unit  = {},
  blocked:        Boolean           = false,
  modifier:       Modifier          = Modifier
) {
  
  /* ====== Component UI */
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier          = modifier
  ) {

    OutlinedTextField(
      value         = value,
      label         = {
        Text(
          text  = settingName,
          style = TextAppearance.label,
          color = if (blocked) ColorAppearance.covertColor else ColorAppearance.onSurfaceColor
        )
      },

      singleLine    = singleLine,
      shape         = ShapeAppearance.oval,
      onValueChange = onValueChange,
      modifier      = Modifier
        .fillMaxWidth()
    )
  }
}