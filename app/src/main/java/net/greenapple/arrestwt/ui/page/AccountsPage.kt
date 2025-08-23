/* Author: Green Apple
 * Date Created: August 12, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.page

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.AssetPaths
import net.greenapple.arrestwt.data.type.AccountData
import net.greenapple.arrestwt.data.type.CardData
import net.greenapple.arrestwt.ui.NavRoute
import net.greenapple.arrestwt.ui.appearance.LayoutAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.SheetButtonEntry
import net.greenapple.arrestwt.ui.component.BackButton
import net.greenapple.arrestwt.ui.component.DefaultPageBox
import net.greenapple.arrestwt.ui.component.FloatingSheetButton
import net.greenapple.arrestwt.ui.component.cards.AccountCard
import net.greenapple.arrestwt.ui.component.cards.ShowcaseCard
import net.greenapple.arrestwt.ui.component.cards.ShowcaseCardEntry
import net.greenapple.arrestwt.ui.component.popups.EditAccountPopup
import net.greenapple.arrestwt.ui.component.popups.EditCardPopup
import net.greenapple.arrestwt.util.data.*
import net.greenapple.arrestwt.util.paths.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import android.content.Context


// ====== ACCOUNTS PAGE ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsPage(
  onAddAccountClick:  () -> Unit = {},
  onAddCardClick:     () -> Unit = {},
  onBack:             () -> Unit = {}
) {

  /* --- Values for page */
  val context: Context  = LocalContext.current
  val lifecycleOwner    = LocalLifecycleOwner.current

  var accounts  by remember { mutableStateOf<List<AccountData>>(emptyList()) }
  var cards     by remember { mutableStateOf<List<CardData>>(emptyList()) }

  fun reload() {
    accounts  = AssetPaths.accountsPath.getAllData<AccountData>(context).orEmpty()
    cards     = AssetPaths.cardsPath.getAllData<CardData>(context).orEmpty()
  }

  var selectedAccount:  AccountData? by remember { mutableStateOf(null) }
  var selectedCard:     CardData?    by remember { mutableStateOf(null) }

  var entries:      List<ShowcaseCardEntry>?  = cards.map {
    ShowcaseCardEntry(
      display = it.uuid.asCardImageName().inCardImages().getImage(context) ?: Icons.Filled.CreditCard,
      details = it.uuid.getCard(context)?.name ?: "",
      onClick = { selectedCard = it }
    )
  }

  /* ====== Create Actions */
  val actions = remember {
    listOf(
      SheetButtonEntry(
        title   = "Add account",
        icon    = NavRoute.AddAccount.icon,
        onClick = onAddAccountClick
      ),

      SheetButtonEntry(
        title   = "Add card",
        icon    = NavRoute.AddCard.icon,
        onClick = onAddCardClick
      )
    )
  }

  LaunchedEffect(Unit) { reload() }
  DisposableEffect(lifecycleOwner) {
    val observer = LifecycleEventObserver { _, e -> if (e == Lifecycle.Event.ON_RESUME) reload() }
    lifecycleOwner.lifecycle.addObserver(observer)
    onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
  }

  /* ====== Start UI */
  Box(
    modifier = Modifier
      .fillMaxSize()
  ) {
    Column() {

      /* === Top Bar */
      CenterAlignedTopAppBar(

        /* --- Back button */
        navigationIcon = { BackButton(onBack = onBack) },

        /* --- Add Account page title */
        title = {
          Text(
            text  = NavRoute.Accounts.label,
            style = TextAppearance.title
          )
        }
      )

      /* === Account Cards */
      if (!accounts.isNullOrEmpty() || !cards.isNullOrEmpty()) {
        LazyColumn {
          
          item {
            ShowcaseCard(
              title = "Cards",
              items = entries
            )
          }

          item {
            Column(
              modifier = Modifier
                .padding(top = 8.dp, bottom = 4.dp)
            ) {
              Text(
                text      = "Accounts",
                textAlign = TextAlign.Center,
                style     = TextAppearance.headingLarge,
                modifier  = Modifier
                  .fillMaxWidth()
              )

              HorizontalDivider(
                thickness = 2.dp,
                modifier  = Modifier
                  .fillMaxWidth()
                  .padding(top = 6.dp, start = 16.dp, end = 16.dp)
              )
            }
          }

          items(accounts) {
            AccountCard(
              account = it,
              onClick = {
                selectedAccount = it
              }
            )
          }
        }

      /* === No Accounts Text */
      } else {
        DefaultPageBox(
          text = "You have not added any accounts!"
        )
      }
    }

    /* ====== Floating Sheet Action Button */
    FloatingSheetButton(
      icon      = NavRoute.AddAccount.icon,
      actions   = actions,
      modifier  = Modifier
        .align(Alignment.BottomEnd)
        .padding(16.dp)
    )
  }

  selectedAccount?.let {
    EditAccountPopup(
      context           = context,
      account           = it,
      onDismissRequest  = {
        selectedAccount = null
        reload()
      }
    )

  } 
  
  selectedCard?.let {
    EditCardPopup(
      context           = context,
      card              = it,
      onDismissRequest  = {
        selectedCard = null
        reload()
      }
    )
  }
}