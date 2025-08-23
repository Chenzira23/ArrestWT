/* Author: Green Apple
 * Date Created: August 23, 2025
 * 
 * Notes:
 */

package net.greenapple.arrestwt.ui.component.popups

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.type.AccountData
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.settings.TextSetting
import net.greenapple.arrestwt.ui.component.settings.CheckboxSetting
import net.greenapple.arrestwt.util.data.*
import net.greenapple.arrestwt.util.paths.*
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.io.File

// ====== EDIT ACCOUNT POPUP COMPONENT =====
@Composable
fun EditAccountPopup(
  context:          Context,
  account:          AccountData,
  onDismissRequest: () -> Unit = {}
) {

  /* --- Component variables */
  var accountName     by remember { mutableStateOf(account.name) }
  var deleteChecked   by remember { mutableStateOf(false) }
  var deleteTextEntry by remember { mutableStateOf("") }

  AlertDialog(
    title = {
      Text(
        text  = "Editing ${account.name}",
        style = TextAppearance.heading
      )
    },

    text = {
      Column() {
        TextSetting(
          value         = accountName,
          settingName   = "Change account name...",
          onValueChange = { accountName = it }
        )

        CheckboxSetting(
          settingName = "Delete Account",
          onChecked   = { deleteChecked = true },
          onUnchecked = { deleteChecked = false }
        )

        if (deleteChecked) {
          val safeDeleteText: String = if (account.name.length > 21) account.name.take(20) else account.name
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
                  account.uuid.asAccountName().inAccounts().deleteFile(context)
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
      Button(
        onClick = {
          account.uuid.asAccountName().inAccounts().editData<AccountData>(context) { it.copy(name = accountName) }
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
      Button(
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