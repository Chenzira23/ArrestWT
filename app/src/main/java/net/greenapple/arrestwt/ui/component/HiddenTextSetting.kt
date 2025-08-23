/* Author: Green Apple
 * Date Created: August 4, 2025
 * 
 * Notes:
 */

package net.greenapple.arrestwt.ui.component

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import kotlinx.coroutines.launch

// ====== HIDDEN TEXT SETTING COMPONENT ======
@Composable
fun HiddenTextSetting(
  settingName:    String,
  loadValue:      suspend ()        -> String,
  onValueChange:  suspend (String)  -> Unit,
  buttonText:     String?           = null,
  modifier:       Modifier          = Modifier
) {

  /* --- Local component values and variables */
  /* scope:
   * 
   * showDialog:
   * If the popup should be shown / the button has been clicked.
   * 
   * hidden:
   * If the text conent in the popup should be hidden. If hidden, it is
   * expected that the text is no longer held in memory for preventing
   * exposure through memory dumping.
   * 
   * text:
   * The raw text in the popup. */
  val scope       = rememberCoroutineScope()
  var showDialog  by remember { mutableStateOf(false) }
  var hidden      by remember { mutableStateOf(true) }
  var text        by remember { mutableStateOf("") }

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

    /* === Button UI */
    Button(
      onClick = { showDialog = true }
    ) {
      Text(
        text  = if (buttonText.isNullOrEmpty()) {
          if (text.isEmpty()) "Set" else "Edit"
        } else {
          buttonText
        },
        style = TextAppearance.label
      )
    }
  }

  /* --- Run when popup opens */
  LaunchedEffect(showDialog) {
    if (showDialog) {
      text = loadValue().orEmpty()

    /* Clear buffer when closed */
    } else {
      text = ""
      hidden = true
    }
  }

  /* --- Popup after button click */
  if (showDialog) {

    /* === Popup UI */
    AlertDialog(

      /* --- Popup title UI */
      title = {
        Text(
          text  = "Enter $settingName",
          style = TextAppearance.heading
        )
      },

      /* --- Popup field text */
      text = {
        OutlinedTextField(

          /* Load item text value */
          value = text,

          /* Default field text UI */
          label = {
            Text(
              text  = settingName,
              style = TextAppearance.subheading
            )
          },
          singleLine = true,

          /* When the value is changed and confirmed */
          onValueChange = { text = it },

          /* Visibility toggle */
          visualTransformation = if (hidden) {
            PasswordVisualTransformation()
          } else {
            VisualTransformation.None
          },

          /* Visibility toggle icon button UI */
          trailingIcon = {
            IconButton(
              onClick = { hidden = !hidden }
            ) {
              Icon(
                imageVector = if (hidden) {
                  Icons.Filled.Visibility
                } else {
                  Icons.Filled.VisibilityOff
                },
                contentDescription = if (hidden) {
                  "show text"
                } else {
                  "hide text"
                }
              )
            }
          },

          modifier = modifier
            .fillMaxWidth()
        )
      },

      /* --- Popup dismiss action */
      onDismissRequest = { showDialog = false },

      /* --- Popup confirm button UI */
      confirmButton = {

        /* Button UI */
        TextButton(
          onClick = {
            scope.launch { onValueChange(text) }
            showDialog = false
          }
        ) {

          /* Text UI */
          Text(
            text  = "Confirm",
            style = TextAppearance.label
          )
        }
      },

      /* --- Popup dismiss button UI */
      dismissButton = {

        /* Button UI */
        TextButton(
          onClick = {
            showDialog = false
          }
        ) {

          /* Text UI */
          Text(
            text  = "Cancel",
            style = TextAppearance.label
          )
        }
      }
    )
  }
}