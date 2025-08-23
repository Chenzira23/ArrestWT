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
import net.greenapple.arrestwt.ui.component.AccountCard
import net.greenapple.arrestwt.ui.component.BackButton
import net.greenapple.arrestwt.ui.component.DefaultPageBox
import net.greenapple.arrestwt.ui.component.FloatingSheetButton
import net.greenapple.arrestwt.ui.component.SheetButtonEntry
import net.greenapple.arrestwt.ui.component.ShowcaseCard
import net.greenapple.arrestwt.ui.component.ShowcaseCardEntry
import net.greenapple.arrestwt.util.data.*
import net.greenapple.arrestwt.util.paths.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.content.Context


// ====== ADD TRANSACTION PAGE ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsPage(
  onBack: () -> Unit = {}
) {

  /* --- Values for page */
  val context:      Context                   = LocalContext.current
  val accounts:     List<AccountData>?        = AssetPaths.accountsPath.getAllData<AccountData>(context)
  val cards:        List<CardData>?           = AssetPaths.cardsPath.getAllData<CardData>(context)
  val entries:      List<ShowcaseCardEntry>?  = cards?.map {
    ShowcaseCardEntry(
      display = it.uuid.asCardImageName().inCardImages().getImage(context) ?: Icons.Filled.CreditCard,
      details = it.uuid.getCard(context)?.name ?: "",
      onClick = {

      }
    )
  }

  /* ====== Create Actions */
  val actions = remember {
    listOf(
      SheetButtonEntry(
        title   = "Add account",
        icon    = NavRoute.AddAccount.icon,
        onClick = { }
      ),

      SheetButtonEntry(
        title   = "Add card",
        icon    = NavRoute.AddAccount.icon,
        onClick = { }
      )
    )
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
      if (accounts != null) {
        LazyColumn {
          
          item {
            ShowcaseCard(
              title = "Cards",
              items = entries
            )
          }

          items(accounts) { AccountCard(it) }
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
}