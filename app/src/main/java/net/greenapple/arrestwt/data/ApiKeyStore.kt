/* Author: Green Apple
 * Date Created: August 5, 2025
 * 
 * Notes: 
 * 
 * Other Dates:
 * > Refactored: August 16, 2025
 * 
 * TODO: Use non-deprecated way of securely storing API keys
 */

package net.greenapple.arrestwt.data

// ====== IMPORTS ======
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

// ====== API KEY STORE ======
/* AV:  Alpha Vantage
 * FMP: Financial Modeling Prep */
class ApiKeyStore(
  context: Context
) {
  /* ====== Create Api Key Keys */
  companion object {
    private val API_KEY_FMP = "api_key_fmp"
    private val API_KEY_AV  = "api_key_av"
  }

  /* ====== Generate Master Key */
  private val masterKey = MasterKey.Builder(context)
    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
    .build()

  /* ====== Create Encrypted SharedPreferences Instance */
  private val keys = EncryptedSharedPreferences.create(
    context,
    "secure_api_keys",
    masterKey,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
  )

  /* ====== Set API Keys */
  fun setApiKeyFmp(value: String) = keys.edit().putString(API_KEY_FMP, value).apply()
  fun setApiKeyAv(value: String)  = keys.edit().putString(API_KEY_AV, value).apply()

  /* ====== Get API Keys */
  fun getApiKeyFmp(): String?     = keys.getString(API_KEY_FMP, null)
  fun getApiKeyAv():  String?     = keys.getString(API_KEY_AV, null)

  /* ====== Check if API Key is Set */
  fun hasApiKeyFmp(): Boolean     = keys.contains(API_KEY_FMP)
  fun hasApiKeyAv():  Boolean     = keys.contains(API_KEY_AV)
}