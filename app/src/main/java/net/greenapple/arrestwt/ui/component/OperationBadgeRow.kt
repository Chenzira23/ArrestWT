/* Author: Green Apple
 * Date Created: August 12, 2025
 * 
 * Other Dates:
 * > Refactored: August 16, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.component

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.type.EntityData
import net.greenapple.arrestwt.ui.appearance.ColorAppearance
import net.greenapple.arrestwt.ui.appearance.ShapeAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import net.greenapple.arrestwt.ui.component.badges.OrgBadge
import net.greenapple.arrestwt.ui.component.badges.OperationBadge
import net.greenapple.arrestwt.ui.component.badges.PersonBadge
import net.greenapple.arrestwt.util.data.getOrg
import net.greenapple.arrestwt.util.data.getPerson
import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Shape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.QuestionMark

// ====== HELPER FUNCITON ======
@Composable
fun addEntityBadges(
  entities:     List<EntityData>,
  hidden:       Boolean = false,
  peopleHidden: Boolean = hidden,
  orgsHidden:   Boolean = hidden,
  size:         Dp,
  context:      Context = LocalContext.current
) {

  entities.forEach { entity ->
    when (entity.type) {
      "person"  -> PersonBadge(entity.id.getPerson(context), peopleHidden, size)
      "org"     -> OrgBadge(entity.id.getOrg(context), orgsHidden, size)
      else      -> PersonBadge(size = size)
    }
  }
}

// ====== OPERATION BADGE ROW ======
@Composable
fun OperationBadgeRow(
  hidden:             Boolean   = false,
  payersHidden:       Boolean   = hidden,
  payersPeopleHidden: Boolean   = payersHidden,
  payersOrgsHidden:   Boolean   = payersHidden,
  payeesHidden:       Boolean   = hidden,
  payeesPeopleHidden: Boolean   = payeesHidden,
  payeesOrgsHidden:   Boolean   = payeesHidden,
  payers:             List<EntityData>?,
  payees:             List<EntityData>?,
  operation:          String,
  size:               Dp        = TextAppearance.iconLabel,
  color:              Color?    = null,
  modifier:           Modifier  = Modifier
) {

  val effectiveColor: Color = color ?: ColorAppearance.flatBackground

  /* ====== Badge Row UI */
  Row(
    horizontalArrangement = Arrangement.spacedBy(4.dp),
    verticalAlignment     = Alignment.CenterVertically,
    modifier              = modifier
  ) {

    /* === Payers Row UI */
    Surface(
      color           = effectiveColor,
      shape           = ShapeAppearance.oval,
      tonalElevation  = 2.dp
    ) {

      Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment     = Alignment.CenterVertically,
        modifier              = Modifier
          .padding(horizontal = 6.dp)
      ) {

        /* --- Display payers in transaction */
        if (payers.isNullOrEmpty()) PersonBadge(size = size)
        else addEntityBadges(payers, hidden, payersPeopleHidden, payersOrgsHidden, size)
      }
    }

    /* === Payees Row UI */
    Surface(
      color           = effectiveColor,
      shape           = ShapeAppearance.oval,
      tonalElevation  = 2.dp
    ) {

      Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment     = Alignment.CenterVertically,
        modifier              = Modifier
          .padding(end = 6.dp)
      ) {

        /* --- Display operation badge */
        OperationBadge(
          operation = operation,
          size      = size
        )

        /* --- Display payees in transaction */
        if (payees.isNullOrEmpty()) {
          Icon(
            imageVector         = Icons.Filled.QuestionMark,
            contentDescription  = "unknown payee icon",
            modifier            = Modifier.size(size)
          )
        }
        else addEntityBadges(payees, hidden, payeesPeopleHidden, payeesOrgsHidden, size)
      }
    }
  }
}