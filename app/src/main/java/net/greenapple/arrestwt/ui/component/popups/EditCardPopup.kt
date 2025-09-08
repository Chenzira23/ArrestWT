/* Author: Green Apple
 * Date Created: August 23, 2025
 * 
 * Notes:
 */

package net.greenapple.arrestwt.ui.component.popups

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.type.CardData
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.settings.CheckboxSetting
import net.greenapple.arrestwt.ui.component.settings.TextSetting
import net.greenapple.arrestwt.util.data.*
import net.greenapple.arrestwt.util.paths.*
import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.io.File

// ====== EDIT CARD POPUP COMPONENT =====
@Composable
fun EditCardPopup(
  context:          Context,
  card:             CardData,
  onDismissRequest: () -> Unit = {}
) {

  /* --- Component variables */
  var cardName        by remember { mutableStateOf(card.name) }
  var deleteChecked   by remember { mutableStateOf(false) }
  var deleteTextEntry by remember { mutableStateOf("") }

  AlertDialog(
    title = {
      Text(
        text  = "Editing ${card.name}",
        style = TextAppearance.heading
      )
    },

    text = {
      Column() {
        TextSetting(
          value         = cardName,
          settingName   = "Change card name...",
          onValueChange = { cardName = it }
        )

        CheckboxSetting(
          settingName = "Delete Card",
          onChecked   = { deleteChecked = true },
          onUnchecked = { deleteChecked = false }
        )

        if (deleteChecked) {
          val safeDeleteText: String = if (card.name.length > 21) card.name.take(20) else card.name
          TextSetting(
            value       = deleteTextEntry,
            settingName = "Enter $safeDeleteText",
            onValueChange = { deleteTextEntry = it }
          )

          if (deleteTextEntry.trim().equals(safeDeleteText)) {
            Row(
              verticalAlignment     = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceEvenly,
              modifier              = Modifier
                .fillMaxWidth()
            ) {

              Button(
                onClick = {
                  card.uuid.deleteCard(context)
                  onDismissRequest()
                }
              ) {
                Text(
                  text  = "Permanently Delete",
                  style = TextAppearance.label
                )
              }
            }
          }
        }
      }
    },

    containerColor = ColorAppearance.backgroundColor,

    onDismissRequest = onDismissRequest,

    confirmButton = {
      TextButton(
        onClick = {
          card.uuid.editCard(context) { it.copy(name = cardName) }
          onDismissRequest()
        }
      ) {

        Text(
          text  = "Save",
          style = TextAppearance.label
        )
      }
    }, 

    dismissButton = {
      TextButton(
        onClick = onDismissRequest
      ) {

        Text(
          text  = "Cancel",
          style = TextAppearance.label
        )
      }
    }
  )
}