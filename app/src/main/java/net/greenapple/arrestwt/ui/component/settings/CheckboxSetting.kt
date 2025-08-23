/* Author: Green Apple
 * Date Created: August 24, 2025
 * 
 * Notes:
 */

package net.greenapple.arrestwt.ui.component.settings

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text

// ====== CHECKBOX SETTING COMPONENT ======
@Composable
fun CheckboxSetting(
  settingName:      String,
  onCheckedChange:  () -> Unit    = {},
  onChecked:        (() -> Unit)? = null,
  onUnchecked:      (() -> Unit)? = null,
  defaultChecked:   Boolean       = false,
  modifier:         Modifier?     = null
) {

  val effectiveModifier: Modifier = modifier
    ?: Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)

  val effectiveOnChecked    = onChecked   ?: onCheckedChange
  val effectiveOnUnchecked  = onUnchecked ?: onCheckedChange

  var checked by remember { mutableStateOf(defaultChecked) }

  Row(
    verticalAlignment     = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier              = effectiveModifier
  ) {

    Text(
      text  = settingName,
      style = TextAppearance.subheading
    )

    Checkbox(
      checked = checked,
      onCheckedChange = {
        checked = it
        if (checked == true) effectiveOnChecked() else effectiveOnUnchecked()
      }
    )
  }
}