/* Author: Green Apple
 * Date Created: August 5, 2025
 * 
 * Note:
 */

package net.greenapple.arrestwt.util

// ====== IMPORTS ======
import java.time.Instant

// ====== SOURCE JSON METADATA ======
data class SourceMeta(
  var updatedAt:  Instant             = Instant.EPOCH,
  val fields:     MutableSet<String>  = mutableSetOf()
)

// ====== PARTIAL STOCK JSON DATA ======
data class StockPartial(
  val symbol:     String,
  val exchange:   String,
  val name:       String,
  val fields:     Map<String, Any>,
  val source:     String,
  val updatedAt:  Instant
)

// ====== STOCK JSON DATA ======
data class StockJson(
  val symbol:     String,
  val exchange:   String,
  var name:       String?                         = null,
  var currency:   String?                         = null,
  var type:       String?                         = null,
  var sources:    MutableMap<String, SourceMeta>  = mutableMapOf(),
  var isActive:   Boolean?                        = null,
  var lastUpdate: Instant                         = Instant.EPOCH
)

/* ====== Get Field From Stock JSON */
fun StockJson.getField(
  field: String
): Any? {
  return when(field) {
    "name"      -> name
    "type"      -> type
    "currency"  -> currency
    "is_active" -> isActive
    else        -> null
  }
}

/* ====== Set Field From Stock JSON */
fun StockJson.setField(
  field: String,
  value: Any?
) {
  when (field) {
    "name"      -> name     = value as? String
    "type"      -> type     = value as? String
    "currency"  -> currency = value as? String
    "is_active" -> isActive = value as? Boolean
  }
}

// ====== STOCK JSON UTILS ======
object StockJsonUtils {

  /* ====== Merge Partial Stock Info into Stock JSON */
  fun shouldReplace(
    field:        String,
    currentValue: Any?,
    newValue:     Any?
  ): Boolean {

    /* === If Field is not Set */
    if (newValue == null)     return false
    if (currentValue == null) return true

    return when(field) {
      /* --- Set name and type */
      "name", "type" -> {
        val currentStr  = currentValue  as? String ?: return true
        val newStr      = newValue      as? String ?: return false

        newStr.isNotBlank() && (newStr.length > currentStr.length || currentStr.isBlank())
      }

      /* --- Set currency */
      "currency" -> {
        val validCurrencyRegex  = Regex("^[A-Z]{3}\$")
        val newStr              = newValue as? String ?: return false
        validCurrencyRegex.matches(newStr)
      }

      /* --- Set if value is still active on exchange */
      "is_active" -> {
        val currentlyActive = currentValue  as? Boolean ?: return true
        val newActive       = newValue      as? Boolean ?: return false
        !currentlyActive && newActive
      }

      /* --- Default to older version of field */
      else -> {
        newValue != currentValue
      }
    }
  }

  /* ====== Merge Partial Stock Info into Stock JSON */
  fun mergeIntoStocks(
    existing: MutableMap<Pair<String, String>, StockJson>,
    partials: List<StockPartial>
  ): Map<Pair<String, String>, StockJson> {

    /* === Add Each Partial of Stock */
    partials.forEach { partial ->
      /* --- Get each partial identified with symbol and exchange as key */
      val key   = Pair(partial.symbol, partial.exchange)
      val stock = existing.getOrPut(key) {
        StockJson(
          symbol    = partial.symbol,
          exchange  = partial.exchange
        )
      }

      /* --- Get each partial field and update them if needed */
      for ((field, value) in partial.fields) {
        val currentValue = stock.getField(field)

        if (currentValue == null 
          || shouldReplace(
            field = field,
            currentValue = currentValue,
            newValue = value
          )
        ) {
          stock.setField(field, value)
          stock.sources[partial.source] = stock.sources
            .getOrDefault(partial.source, SourceMeta())
            .apply {
              updatedAt = partial.updatedAt
              fields.add(field)
            }
        }
      }
      stock.lastUpdate = maxOf(stock.lastUpdate, partial.updatedAt)
    }
    
    return existing
  }
} 