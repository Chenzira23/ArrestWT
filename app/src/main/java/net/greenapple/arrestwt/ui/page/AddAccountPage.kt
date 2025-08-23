/* Author: Green Apple
 * Date Created: August 10, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.page

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.NavRoute
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.BackButton
import net.greenapple.arrestwt.ui.component.settings.TextSetting
import net.greenapple.arrestwt.ui.component.settings.DropdownSetting
import net.greenapple.arrestwt.util.data.CreateFile
import net.greenapple.arrestwt.util.paths.asAccountName
import net.greenapple.arrestwt.util.paths.inAccounts
import net.greenapple.arrestwt.data.type.AccountData
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import java.math.BigDecimal
import java.util.UUID
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// ====== ADD ACCOUNT PAGE ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountPage(
  onBack: () -> Unit = {}
) {

  val context         = LocalContext.current

  var accountName     by remember { mutableStateOf("") }
  var accountType     by remember { mutableStateOf("cash") }
  var accountBalance  by remember { mutableStateOf("") }

  /* ====== Start UI */
  Column() {

    /* === Top Bar */
    CenterAlignedTopAppBar(

      /* --- Back button */
      navigationIcon = { BackButton(onBack = onBack) },

      /* --- Add Account page title */
      title = {
        Text(
          text  = NavRoute.AddAccount.label,
          style = TextAppearance.title
        )
      }
    )

    /* === Configure Account */
    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(24.dp),
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
      item {
        DropdownSetting(
          settingName       = "Account type",
          options           = listOf("cash", "debt", "invest"),
          selectedOption    = accountType,
          onOptionSelected  = { accountType = it }
        )
      }

      item {
        TextSetting(
          value         = accountName,
          settingName   = "Account name...",
          onValueChange = { accountName = it }
        )
      }

      item {
        TextSetting(
          value         = accountBalance,
          settingName   = "Initial balance...",
          onValueChange = { accountBalance = it }
        )
      }

      item {
        val balance: BigDecimal = accountBalance
          .replace(",", "")
          .replace("$", "")
          .toBigDecimalOrNull()
        ?: BigDecimal.ZERO

        val currentData: AccountData = AccountData(
          uuid      = UUID.randomUUID().toString(),
          name      = accountName.trim(),
          type      = accountType,
          balance   = balance,
          hasImage  = false
        )

        Row(
          verticalAlignment     = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceEvenly,
          modifier              = Modifier
            .fillMaxWidth()
        ) {
          Button(
            onClick   = {
              CreateFile(
                context = context,
                path    = currentData.uuid.asAccountName().inAccounts(),
                data    = Json.encodeToString(currentData)
              )

              onBack()
            }
          ) {
            Text(
              text  = "Confirm Account Creation",
              style = TextAppearance.label
            )
          }
        }
      }
    }
  }
}