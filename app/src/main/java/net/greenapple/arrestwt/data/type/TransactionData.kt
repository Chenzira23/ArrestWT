/* Author: Green Apple
 * Date Created: August 11, 2025
 * 
 * Other Dates:
 * > Refactored: August 15, 2025
 * 
 * Notes: Represents a transaction and its data
 */

package net.greenapple.arrestwt.data.type

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.type.CardData
import net.greenapple.arrestwt.data.type.EntityData
import net.greenapple.arrestwt.util.serialization.BigDecimalAsStringSerializer
import net.greenapple.arrestwt.util.serialization.InstantAsIso8601StringSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.Instant

// ====== TRANACTION DATA ======
/* uuid:
 * Unique identifier for every transaction.
 * 
 * time:
 * Time and date in ISO-8601 format as a string, serialized into a time Instant
 * Used for filtering and ordering in transaction listings.
 * 
 * name:
 * Name or title of the transaction. Intended to be short but identifiable at
 * a glance.
 * 
 * description:
 * Description of the transaction. Intended to be longer but more detailed as
 * to what the transaction is about.
 * 
 * type:
 * Type of the transaction. Options are:
 * deposit  = Add money to an account
 * withdraw = Remove money from an account 
 * buy      = Buy an investment/commodity in an investment account
 * sell     = Sell an investment/commodity in an investment account
 * inkind   = Transfer investment/commodity "in-kind" between investment accounts
 * 
 * payers:
 * List of the entities that are losing money in the transaction.
 * 
 * payees:
 * List of the entities that are gaining money in the transaction.
 * 
 * card:
 * The uuid of the card used for the transaction (on the payers side).
 * 
 * tags:
 * The tags held by the transaction. Used for filtering and context of transactions.
 * 
 * value:
 * The absolute value of the transaction as a String and treated as a BigDecimal.
 * 
 * TODO:
 * - Add currency types
 */
@Serializable
data class TransactionData(
  val uuid:         String,
  @Serializable(with = InstantAsIso8601StringSerializer::class)
  val time:         Instant,
  val name:         String?             = null,
  val description:  String?             = null,
  val type:         String              = "buy",
  val payer:        List<EntityData>?   = null,
  val payee:        List<EntityData>?   = null,
  val card:         String,
  val tags:         List<String>?       = null,
  @Serializable(with = BigDecimalAsStringSerializer::class)
  val value:        BigDecimal          = BigDecimal.ZERO
)