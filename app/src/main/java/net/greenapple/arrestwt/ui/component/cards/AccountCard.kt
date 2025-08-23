/* Author: Green Apple
 * Date Created: August 22, 2025
 * 
 * Note:
 */


package net.greenapple.arrestwt.ui.component.cards

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.LayoutAppearance
import net.greenapple.arrestwt.ui.appearance.ShapeAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.data.type.AccountData
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import android.icu.math.BigDecimal

// ====== ACCOUNT CARD COMPONENT ======
@Composable
fun AccountCard(
  account:  AccountData,
  hidden:   Boolean     = false,
  colors:   CardColors? = null,
  onClick:  () -> Unit  = {},
  modifier: Modifier?   = null
) {

  val effectiveColors: CardColors = colors
    ?: CardDefaults.cardColors(
      containerColor = ColorAppearance.containerColor
    )

  val effectiveModifier: Modifier = modifier
    ?: LayoutAppearance.primaryContainerModifier

  /* ====== Card UI */
  Card(
    shape     = ShapeAppearance.primaryContainer,
    colors    = effectiveColors,
    onClick   = onClick,
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

        /* --- Account name */
        Text(
          text  = account.name,
          style = TextAppearance.heading
        )
        
        Text(
          text  = "$" + account.balance.toString(),
          style = TextAppearance.valueMedium
        )
      }

      /* --- Add line below title */
      HorizontalDivider(
        thickness = 2.dp,
        color     = ColorAppearance.covertColor,
        modifier  = Modifier.fillMaxWidth()
      )

      /* --- Account type */
      Text(
        text  = "Type: " +  account.type,
        style = TextAppearance.body,
        color = ColorAppearance.covertColor
      )

      /* --- Account uuid */
      Text(
        text  = account.uuid,
        style = TextAppearance.body,
        color = ColorAppearance.covertColor
      )
    }
  }
}