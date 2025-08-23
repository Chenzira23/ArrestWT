/* Author: Green Apple
 * Date Created: August 12, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.viewmodel

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.DataStore
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VisibilityViewModel(
  private val dataStore:  DataStore
): ViewModel() {

  /* ====== Hold Global Visibility */
  private val valuesHidden                  = MutableStateFlow(false)
  val valuesHiddenFlow: StateFlow<Boolean>  = valuesHidden.asStateFlow()

  /* ====== Load Global Visibility into StateFlow */
  init {
    viewModelScope.launch {
      dataStore.valuesHiddenFlow.collect { isHidden ->
        valuesHidden.value = isHidden
      }
    }
  }

  /* ====== Set Global Visibility */
  fun setValuesHidden(
    hidden: Boolean
  ) {
    valuesHidden.value = hidden
    viewModelScope.launch { dataStore.setValuesHidden(hidden) } 
  }

  /* ====== Get Global Visibility */
  suspend fun getValuesHidden(): Boolean {
    return dataStore.getValuesHidden()
  }
}