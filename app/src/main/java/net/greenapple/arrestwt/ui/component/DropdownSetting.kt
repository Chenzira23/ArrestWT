/* Author: Green Apple
 * Date Created: August 1, 2025
 * 
 * Note: Button component for a SettingsPage listing
 */

package net.greenapple.arrestwt.ui.component

/* Imports */
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.greenapple.arrestwt.ui.appearance.TextAppearance

// ====== DROPDOWN SETTING COMPONENT ======
@Composable
fun DropdownSetting(
  settingName:      String,
  options:          List<String>,
  selectedOption:   String,
  onOptionSelected: (String) -> Unit,
  modifier:         Modifier      = Modifier
) {

  /* --- Local component values and variables */
  /* epanded:
   * If the dropdown is expanded */
  var expanded by remember { mutableStateOf(false) }

  /* ====== Component UI */
  Row(
    verticalAlignment     = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier              = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  ) {

    /* === Setting Name UI */
    Text(
      text  = settingName,
      style = TextAppearance.subheading
    )

    /* === Dropdown UI */
    Box {

     /* --- Dropdown button */
      TextButton(
        contentPadding = PaddingValues(0.dp),
        onClick = { expanded = !expanded }
      ) {

        /* --- Dropdown selected option text */
        Text(
          text  = selectedOption,
          style = TextAppearance.label
        )

        /* --- Dropdown indicator arrow icon */
        Icon(
          imageVector         = if (expanded) {
            Icons.Filled.ArrowDropUp
          } else Icons.Filled.ArrowDropDown,
          contentDescription  = if (expanded) "collapse" else "expand"
        )
      }

      /* --- Dropdown popup menu */
      DropdownMenu(
        expanded          = expanded,
        onDismissRequest  = { expanded = false }
      ) {

        /* Get all option items */
        options.forEach { option ->
          DropdownMenuItem(

            text = { 
              Text(
                text  = option,
                style = TextAppearance.label
                ) 
            },

            onClick   = {
              onOptionSelected(option)
              expanded = false
            }
          )
        }
      }
    }
  }
}