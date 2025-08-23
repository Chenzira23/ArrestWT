/* Author: Green Apple
 * Date Created: August 9, 2025
 * 
 * Other Dates:
 * > Refactored: August 15, 2025
 * 
 * Notes: Represents an account and its data
 */

package net.greenapple.arrestwt.data.type

// ====== IMPORTS ======
import net.greenapple.arrestwt.util.serialization.BigDecimalAsStringSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

// ====== ACCOUNT DATA ======
/* uuid:
 * Unique identifier for every account.
 * 
 * name:
 * Name of an account. Used to differentiate between accounts.
 * 
 * type:
 * The account type. Options are:
 * cash   = General cash account
 * debt   = Account in which the user owes money back later
 * invest = Investment account for stocks, etfs, commodities, etc.
 * 
 * balance:
 * The current balance of the account. Changes depending on type:
 * cash   = General balance of the account
 * debt   = The current limit on what can be borrowed
 * invest = The last updated total value of the account
 * 
 * hasImage:
 * If the person has a profile picture to represent them
 */
@Serializable
data class AccountData(
  val uuid:             String,
  val name:             String,
  val type:             String          = "cash",
  @Serializable(with = BigDecimalAsStringSerializer::class)
  val balance:          BigDecimal      = BigDecimal.ZERO,
  val hasImage:         Boolean         = false
)