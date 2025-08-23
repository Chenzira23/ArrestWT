/* Author: Green Apple
 * Date Created: August 5, 2025
 * 
 * Other Dates:
 * > Refactored: August 16, 2025
 * 
 * Notes: Stores application data through app open and close
 */

package net.greenapple.arrestwt.data

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.NavRoute
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/* ====== Create Settings DataStore Instance */
val Context.dataStore by preferencesDataStore(name = "settings")

// ====== DATA STORE ======
class DataStore(
  val context: Context
) {

  /* === Create Settings Item Keys */
  companion object {
    private val SELECTED_THEME  = stringPreferencesKey("selected_theme")
    private val STARTING_PAGE   = stringPreferencesKey("starting_page")
    private val VALUES_HIDDEN   = booleanPreferencesKey("values_hidden")
  }

  /* === Read Settings Items as Flows */
  val selectedThemeFlow:          Flow<String>    = context.dataStore.data.map { it[SELECTED_THEME] ?: "Dark" }
  val selectedStartingPageFlow:   Flow<String>    = context.dataStore.data.map { it[STARTING_PAGE]  ?: NavRoute.Home.route }
  val valuesHiddenFlow:           Flow<Boolean>   = context.dataStore.data.map { it[VALUES_HIDDEN]  ?: true }

  /* === Set Setting Items */
  suspend fun setSelectedTheme(s: String)         = context.dataStore.edit { it[SELECTED_THEME] = s }
  suspend fun setSelectedStartingPage(s: String)  = context.dataStore.edit { it[STARTING_PAGE]  = s }
  suspend fun setValuesHidden(s: Boolean)         = context.dataStore.edit { it[VALUES_HIDDEN]  = s }

  /* === Get Setting Items */
  suspend fun getValuesHidden():  Boolean         = context.dataStore.data.map { it[VALUES_HIDDEN] ?: true }.first()
}
