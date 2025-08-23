/* Author: Green Apple
 * Date Created: August 10, 2025
 * 
 * Note:
 */

package net.greenapple.arrestwt.ui.component

// ====== IMPORTS ======
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew

// ====== BACK BUTTON COMPONENT ======
@Composable
fun BackButton(
  onBack: () -> Unit = {}
) {
  
  /* ====== Component UI */
  IconButton(onClick = onBack) {
    Icon(
      imageVector         = Icons.Filled.ArrowBackIosNew,
      contentDescription  = "back"
    )
  }
}