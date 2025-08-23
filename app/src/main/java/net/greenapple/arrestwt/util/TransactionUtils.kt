/* Author: Green Apple
 * Date Created: August 11, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.util

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.type.TransactionData
import android.content.Context
import android.util.Log
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import java.io.IOException
import kotlinx.serialization.SerializationException

// ====== TRANSACTION UTILS ======
object TransactionUtils {
  fun loadTransactionsFromJson(
    context:    Context,
    fileName:   String,
  ): List<TransactionData>? {

    /* === Try to Laod JSON String From File */
    return try {
      val jsonStr = context.assets
        .open("user/transactions/$fileName.transactions.json")
        .bufferedReader()
        .use {
          it.readText()
        }
      
      Json.decodeFromString<List<TransactionData>>(jsonStr)

    } catch (e: IOException) {
      Log.e("TransactionUtils", "I/O error reading $fileName.transactions.json", e)
      e.printStackTrace()
      null
    } catch (e: SerializationException) {
      Log.e("TransactionUtils", "Parsing error reading $fileName.transactions.json", e)
      e.printStackTrace()
      null
    }
  }
}