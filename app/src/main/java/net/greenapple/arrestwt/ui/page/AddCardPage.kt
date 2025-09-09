/* Author: Green Apple
 * Date Created: August 23, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.page

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.type.AccountData
import net.greenapple.arrestwt.data.type.CardData
import net.greenapple.arrestwt.ui.NavRoute
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.BackButton
import net.greenapple.arrestwt.ui.component.settings.DropdownSettingData
import net.greenapple.arrestwt.ui.component.settings.TextSetting
import net.greenapple.arrestwt.util.data.*
import net.greenapple.arrestwt.util.paths.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.Lifecycle
import android.content.Context
import java.util.UUID
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// ====== ADD CARD PAGE ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardPage(
   onBack: () -> Unit = {}
) {

  val context: Context  = LocalContext.current
  val lifecycleOwner    = LocalLifecycleOwner.current

  var cardName        by  remember { mutableStateOf("") }
  var cardAccounts    =   remember { mutableStateListOf<AccountData?>(null) }
  var accounts        by  remember { mutableStateOf<List<AccountData>>(emptyList()) }

  fun reload() {
    accounts = filePaths.accountsPath.getAllData<AccountData>(context).orEmpty()
  }

  LaunchedEffect(Unit) { reload() }
  DisposableEffect(lifecycleOwner) {
    val observer = LifecycleEventObserver { _, e -> if (e == Lifecycle.Event.ON_RESUME) reload() }
    lifecycleOwner.lifecycle.addObserver(observer)
    onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
  }

  /* ====== Start UI */
  Column() {

    /* === Top Bar */
    CenterAlignedTopAppBar(

      /* --- Back button */
      navigationIcon = { BackButton(onBack = onBack) },

      /* --- Add Account page title */
      title = {
        Text(
          text  = NavRoute.AddCard.label,
          style = TextAppearance.title
        )
      }
    )

    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(24.dp),
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
      item {
        TextSetting(
          value         = cardName,
          settingName   = "Card name...",
          onValueChange = { cardName = it }
        )
      }

      itemsIndexed(cardAccounts) { index, sel ->
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier          = Modifier
            .fillMaxWidth()
        ) {

          DropdownSettingData(
            settingName       = "Select account (${index + 1})",
            options           = accounts.map { it to "${it.name} $${"%,.2f".format(it.balance)}" },
            selectedOption    = sel?.let { it to "${it.name} $${"%,.2f".format(it.balance)}" },
            onOptionSelected  = { cardAccounts[index] = it.first }
          )

          if (cardAccounts.size > 1) {
            IconButton(
              onClick = { cardAccounts.removeAt(index) }
            ) {
              Icon(
                imageVector         = Icons.Filled.Close,
                contentDescription  = "remove account"
              )
            }
          }
        }
      }

      item {
        Button(
          onClick   = { cardAccounts.add(null) },
          modifier  = Modifier
            .fillMaxWidth()
        ) {

          Text(
            text  = "Attach another account",
            style = TextAppearance.label
          )
        }
      }

      item {
        val currentData: CardData = CardData(
          uuid      = UUID.randomUUID().toString(),
          name      = cardName.trim(),
          accounts  = cardAccounts.map { it?.uuid }
        )

        Row(
          verticalAlignment     = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceEvenly,
          modifier              = Modifier
            .fillMaxWidth()
        ) {
          Button(
            onClick = {
              CreateFile(
                context = context,
                path    = currentData.uuid.asCardName().inCards(),
                data    = Json.encodeToString(currentData)
              )

              onBack()
            }
          ) {
            Text(
              text  = "Confirm Card Creation",
              style = TextAppearance.label
            )
          }
        }
      }
    }
  }
}