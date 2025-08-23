/* Author: Green Apple
 * Date Created: August 1, 2025
 * 
 * Note: Code for the search page and its information
 */

package net.greenapple.arrestwt.ui.page

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.NavRoute
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import net.greenapple.arrestwt.ui.component.DefaultPageBox
import net.greenapple.arrestwt.ui.appearance.TextAppearance

// ====== SEARCH PAGE ======
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage()
{
  /* Search related values and variables */
  val context = LocalContext.current
  var query by remember { mutableStateOf("") }
  
  /* Start UI */
  Column(
    modifier = Modifier
      .fillMaxSize()
  ) {
    /* ====== TOP BAR ====== */
    CenterAlignedTopAppBar(
      title = {
        Text(
          text  = NavRoute.Search.label,
          style = TextAppearance.title
        )
      }
    )

    /* ====== SEARCH BAR ====== */
    OutlinedTextField(
      value         = query,
      label         = {
        Text(
          text  = "Search symbols...",
          style = TextAppearance.label,
        )
      },
      singleLine    = true,
      shape         = RoundedCornerShape(50),
      onValueChange = { query = it },
      modifier      = Modifier
        .fillMaxWidth()
        .padding(16.dp),

      /* Cancel search button */
      trailingIcon = {
        if (query.isNotEmpty()) {
          /* Create circle around button */
          Surface(
            shape           = RoundedCornerShape(100),
            color           = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
            tonalElevation  = 2.dp
          ) {
            /* Create button */
            IconButton(
              onClick   = { query = "" },
              modifier  = Modifier
                .padding(end = 4.dp)
            ) {
              Icon(
                imageVector         = Icons.Default.Close,
                contentDescription  = "clear search"
              )
            }
          }
        }
      }
    ) /* !! Search bar end */

    /* ====== SPACER ====== */
    /* Spacer between search bar and list items */
    Spacer(
      modifier = Modifier
        .height(16.dp)
    ) /* !! Spacer end */

    /* ====== STOCK LIST ====== */
    /* Display list of stocks only if a query is active */
    if (query.isNotBlank()) {
      /* List items in a scrollable LazyColumn */
      LazyColumn {
        // TODO: Add stock logic
      }

    /* !! List of stocks end */
    /* If there is no query, display the discover page UI */
    } else {
      /* ====== DISCOVER UI ====== */
      DefaultPageBox(
        text  = "Search some symbols to get started!",
      )
    }
  }
}