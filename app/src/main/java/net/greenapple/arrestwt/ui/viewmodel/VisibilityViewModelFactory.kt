/* Author: Green Apple
 * Date Created: August 12, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.viewmodel

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.DataStore
import net.greenapple.arrestwt.ui.viewmodel.VisibilityViewModel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// ====== VISIBILITY VIEWMODEL FACTORY ======
class VisibilityViewModelFactory(
  private val context:    Context
): ViewModelProvider.Factory {

  /* ====== Override ViewModelProvider Factory Create Function */
  override fun <T: ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(VisibilityViewModel::class.java)) {
      return VisibilityViewModel(
        dataStore = DataStore(context)
      ) as T
    }

    /* Throw if not castable to SettingsViewModel */
    throw IllegalArgumentException("Unknown ViewModel Class")
  }
}