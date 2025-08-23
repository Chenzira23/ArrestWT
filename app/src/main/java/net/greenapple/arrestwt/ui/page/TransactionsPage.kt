/* Author: Green Apple
 * Date Created: August 9, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.page

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.NavRoute
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.DefaultPageBox
import net.greenapple.arrestwt.ui.component.FloatingSheetButton
import net.greenapple.arrestwt.ui.component.SheetButtonEntry
import net.greenapple.arrestwt.ui.component.VisibilityToggleButton
import net.greenapple.arrestwt.ui.component.cards.TransactionCard
import net.greenapple.arrestwt.ui.viewmodel.VisibilityViewModel
import net.greenapple.arrestwt.data.type.TagData
import net.greenapple.arrestwt.util.TransactionUtils
import net.greenapple.arrestwt.util.TagUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import kotlin.collections.emptyList

// ====== TRANSACTIONS PAGE ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsPage(
  visibilityViewModel:  VisibilityViewModel,
  onAccountsClick:        () -> Unit = {},
  onAddTransactionClick:  () -> Unit = {},
  onAddAccountClick:      () -> Unit = {},
  onAddTagsClick:         () -> Unit = {}
) {

  /* --- Values for page */
  val context             = LocalContext.current
  val recentTransactions  = remember { TransactionUtils.loadTransactionsFromJson(context, "2025-09") }
  val hidden              by visibilityViewModel.valuesHiddenFlow.collectAsState()

  /* ====== Create Actions */
  val actions = remember {
    listOf(
      SheetButtonEntry(
        title   = "Add transaction",
        icon    = NavRoute.AddTransaction.icon,
        onClick = { onAddTransactionClick() }
      ),

      SheetButtonEntry(
        title   = "Add account",
        icon    = NavRoute.AddAccount.icon,
        onClick = { onAddAccountClick() }
      ),

      SheetButtonEntry(
        title ="Add tag",
        icon  = NavRoute.AddTag.icon,
        onClick = { onAddTagsClick() }
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
        navigationIcon = {
          Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment     = Alignment.CenterVertically,
            modifier              = Modifier
              .padding(end = 6.dp)
          ) {

            /* --- Add transaction filter button */
            IconButton(
              onClick = {}
            ) {
              Icon(
                imageVector         = Icons.Outlined.FilterList,
                contentDescription  = "Filter Transactions"
              )
            }

            /* --- Add visibility toggle button */
            VisibilityToggleButton(
              visibilityViewModel = visibilityViewModel,
              hidden              = hidden
            )
          }
        },
        title = {
          Text(
            text  = NavRoute.Transactions.label,
            style = TextAppearance.title
          )
        },

        actions = {
          /* --- Add accounts navigation button */
          IconButton(
            onClick = onAccountsClick
          ) {
            Icon(
              imageVector         = NavRoute.Accounts.icon,
              contentDescription  = NavRoute.Accounts.label
            )
          }
        }
      )

      /* --- Check if there are recent transactions */
      if (recentTransactions !=  null) {
        LazyColumn {

          /* ====== Transactions */
          items(recentTransactions) { transaction ->
            val tagList by produceState(initialValue = emptyList<TagData>(), transaction.tags) {
              value = transaction.tags?.mapNotNull { tagId ->
                TagUtils.loadTagFromJsonOrDefualt(
                  context = context,
                  id      = tagId
                )
              }?: emptyList()
            }

            TransactionCard(
              transaction = transaction,
              hidden      = hidden,
              tags        = tagList
            )
          }
        }
      } else {

        /* ====== No Transactions Default Body */
        DefaultPageBox(
          text  = "Transactions could no be loaded...",
        )
      }
    }

    /* ====== Floating Sheet Action Button */
    FloatingSheetButton(
      icon      = Icons.Default.AddCircle,
      rotate    = true,
      actions   = actions,
      modifier  = Modifier
        .align(Alignment.BottomEnd)
        .padding(16.dp)
    )
  }
}