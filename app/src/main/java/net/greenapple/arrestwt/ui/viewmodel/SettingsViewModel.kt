/* Author: Green Apple
 * Date Created: August 5, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.ui.viewmodel

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.DataStore
import net.greenapple.arrestwt.data.ApiKeyStore
import net.greenapple.arrestwt.ui.NavRoute
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import android.content.Context

// ====== SETTINGS VIEWMODEL ======
class SettingsViewModel(
  context:                Context,
  private val dataStore:  DataStore
): ViewModel() {

  /* === Hold Selected Theme */
  private val selectedTheme                       = MutableStateFlow("Dark")
  val selectedThemeFlow: StateFlow<String>        = selectedTheme.asStateFlow()

  /* === Hold Selected Starting Page */
  val allowedStartingPages                        = NavRoute.bottomNavRoutes.map { it.route }.toSet()
  private val selectedStartingPage                = MutableStateFlow(NavRoute.Home.route)
  val selectedStartingPageFlow: StateFlow<String> = selectedStartingPage.asStateFlow()

  /* === Hold Secure API Key Store */
  private val apiKeyStore                         = ApiKeyStore(context)

  /* === Holds if API Keys Have Been Set */
  /* --- Financial modeling prep */
  private val hasApiKeyFmp                        = MutableStateFlow(apiKeyStore.hasApiKeyFmp())
  val hasApiKeyFmpFlow                            = hasApiKeyFmp.asStateFlow()

  /* --- Alpha vantage */
  private val hasApiKeyAv                         = MutableStateFlow(apiKeyStore.hasApiKeyAv())
  val hasApiKeyAvFlow                             = hasApiKeyAv.asStateFlow()

  /* === Load Settings into StateFlows */
  init {

    /* --- Selected theme */
    viewModelScope.launch {
      dataStore.selectedThemeFlow.collect { setting ->
        selectedTheme.value = setting
      }
    }

    /* --- Selected starting page */
    viewModelScope.launch {
      dataStore.selectedStartingPageFlow.onEach { route ->
        selectedStartingPage.value = if (route in allowedStartingPages) {
          route
        } else {
          NavRoute.Home.route
        }
      }.launchIn(viewModelScope)
    }
  }

  /* === Set Settings */
  /* --- Set selected theme */
  fun setSelectedTheme(
    setting: String
  ) {
    selectedTheme.value = setting
    viewModelScope.launch {
      dataStore.setSelectedTheme(setting)
    }
  }

  /* --- Set starting page theme */
  fun setSelectedStartingPage(
    setting: String
  ) {
    val safe = if (setting in allowedStartingPages) setting else NavRoute.Home.route
    selectedStartingPage.value = safe
    viewModelScope.launch {
      dataStore.setSelectedStartingPage(safe)
    }
  }

  /* --- Set financial modeling prep API key */
  fun onApiKeyFmpEntered(
    setting: String
  ) {
    viewModelScope.launch {
      apiKeyStore.setApiKeyFmp(setting)
      
      if (setting.isEmpty()) {
        hasApiKeyFmp.value = false
      } else {
        hasApiKeyFmp.value = true
      }
    }
  }

  /* --- Set alpha vantage API key */
  fun onApiKeyAvEntered(
    setting: String
  ) {
    viewModelScope.launch {
      apiKeyStore.setApiKeyAv(setting)

      if (setting.isEmpty()) {
        hasApiKeyAv.value = false
      } else {
        hasApiKeyAv.value = true
      }
    }
  }

  /* === Get Settings */
  /* --- Get financial modeling prep API key JIT */
  suspend fun loadApiKeyFmp(): String {
    return apiKeyStore.getApiKeyFmp().orEmpty()
  }

  /* --- Get alpha vantage API key JIT */
  suspend fun loadApiKeyAv(): String {
    return apiKeyStore.getApiKeyAv().orEmpty()
  }
}