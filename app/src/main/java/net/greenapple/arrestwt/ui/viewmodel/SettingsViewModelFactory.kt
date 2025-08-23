/* Author: Green Apple
 * Date Created: August 5, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.viewmodel

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.DataStore
import net.greenapple.arrestwt.ui.viewmodel.SettingsViewModel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// ====== SETTINGS VIEWMODEL FACTORY ======
class SettingsViewModelFactory(
  private val context:    Context
): ViewModelProvider.Factory {

  /* ====== Override ViewModelProvider Factory Create Function */
  override fun <T: ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
      return SettingsViewModel(
        context   = context,
        dataStore = DataStore(context)
      ) as T
    }

    /* Throw if not castable to SettingsViewModel */
    throw IllegalArgumentException("Unknown ViewModel Class")
  }
}