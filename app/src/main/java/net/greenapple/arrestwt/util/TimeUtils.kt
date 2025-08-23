/* Author: Green Apple
 * Date Created: August 12, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.util

// ====== IMPORTS ======
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

// ====== TAG UTILS ======
object TimeUtils {

  /* ====== Format Instant for Transactions as a String */
  fun instantToTransactionTimeStr(
    time: Instant
  ): String {

    val formatter = DateTimeFormatter
      .ofPattern("HH:mm - MMM d, yyyy", Locale.getDefault())
      .withZone(ZoneId.systemDefault())

    return formatter.format(time)
  }
}