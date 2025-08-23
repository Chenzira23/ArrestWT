/* Author: Green Apple
 * Date Created: August 10, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.component

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.LayoutAppearance
import net.greenapple.arrestwt.ui.appearance.ShapeAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.badges.CardBadge
import net.greenapple.arrestwt.ui.component.badges.TagBadge
import net.greenapple.arrestwt.ui.component.OperationBadgeRow
import net.greenapple.arrestwt.data.type.TagData
import net.greenapple.arrestwt.data.type.TransactionData
import net.greenapple.arrestwt.util.TagUtils
import net.greenapple.arrestwt.util.TimeUtils
import net.greenapple.arrestwt.util.data.getCard
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CardColors
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import android.content.Context

// ====== TRANSACTION CARD COMPONENT ======
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TransactionCard(
  transaction:  TransactionData,
  hidden:       Boolean     = false,
  tags:         List<TagData>,
  colors:       CardColors? = null,
  modifier:     Modifier?   = null
) {

  val effectiveColors: CardColors = colors
    ?: CardDefaults.cardColors(
      containerColor = ColorAppearance.containerColor
    )

  val effectiveModifier: Modifier = modifier
    ?: LayoutAppearance.primaryContainerModifier

  val context = LocalContext.current

  /* ====== Card UI */
  Card(
    shape     = ShapeAppearance.primaryContainer,
    colors    = effectiveColors,
    modifier  = effectiveModifier
  ) {

    Column(
      horizontalAlignment = Alignment.Start,
      modifier            = LayoutAppearance.withinContainerCol
    ) {

      /* === Title */
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically,
        modifier              = Modifier
          .fillMaxWidth()
      ) {

        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {

          /* --- Transaction name */
          Text(
            text  = transaction.name ?: "",
            style = TextAppearance.subheading
          )

          /* Spacer between name and card icon */
          Spacer(
            modifier = Modifier
              .padding(8.dp)
          )

          /* --- Card for transaction icon */
          CardBadge(transaction.card.getCard(context), hidden)
        }
          
        /* --- Person/People of transaction icon */
        OperationBadgeRow(
          hidden      = hidden,
          payers      = transaction.payer,
          payees      = transaction.payee,
          operation   = transaction.type
        )
      }

      /* --- Add line below title */
      HorizontalDivider(
        thickness = 2.dp,
        color     = ColorAppearance.covertColor,
        modifier  = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp)
      )

      /* === Details */
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically,
        modifier              = Modifier
          .fillMaxWidth()
      ) {

        Text(
          text  = transaction.description ?: "",
          style = TextAppearance.body,
          color = ColorAppearance.covertColor
        )

        /* --- Funds value */
        Text(
          text  = if (hidden) "$••,•••.••" else "$" + transaction.value.toString(),
          style = TextAppearance.valueSmall
        )
      }

      /* === Date */
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier          = Modifier
          .fillMaxWidth()
      ) {
        /* --- Date text */
        Text(
          /* Do not show date if funds are hidden */
          text  = if (hidden) {
            "••:•• - ••• ••, ••••"

          /* Show date if funds are not hidden */
          } else {
            TimeUtils.instantToTransactionTimeStr(
              time = transaction.time
            )
          },
          style = TextAppearance.body,
          color = ColorAppearance.covertColor
        )
      }

      /* --- Add spacer before tags */
      Spacer(
        modifier = Modifier
          .padding(4.dp)
      )

      /* === Tags */
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement   = Arrangement.spacedBy(4.dp),
        modifier              = Modifier
          .fillMaxWidth()
      ) {

        /* --- UI for each tag */
        tags.forEach { tag ->

          /* Box to ensure proper alignment */
          Box(
            contentAlignment = Alignment.Center
          ) {
            TagBadge(tag)
          }
        }
      }
    }
  }
}