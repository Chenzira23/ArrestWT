/* Author: Green Apple
 * Date Created: August 5, 2025
 * 
 * Note:
 */

package net.greenapple.arrestwt.ui.component

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.appearance.LayoutAppearance
import net.greenapple.arrestwt.ui.appearance.TextAppearance
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text

// ====== DEFAULT PAGE BOX ======
@Composable
fun DefaultPageBox(
  text:     String,
  modifier: Modifier? = null
) {

  val effectiveModifier: Modifier = modifier
    ?: Modifier
      .fillMaxSize()
      .padding(horizontal = LayoutAppearance.primaryPadding)
  

  /* ====== Component UI */
  Box(
    contentAlignment  = Alignment.Center,
    modifier          = effectiveModifier
      
  ) {
    
    /* === Default Message Text */
    Text(
      text  = text,
      style = TextAppearance.body.copy(textAlign = TextAlign.Center),
    )
  }
}