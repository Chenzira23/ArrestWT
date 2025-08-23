/* Author: Green Apple
 * Date Created: August 5, 2025
 * 
 * Note:
 */

package net.greenapple.arrestwt.util

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.DataStore // TODO: Store API keys in a better way
import android.content.Context

// ====== STOCK LIST SOURCE UTILS =====
object StockListSourceUtils {

  /* ====== Create Stock List Soruce Structure */
  private data class StockListSource(
    val fileUrl:    String,
    val fileType:   String  = "json",
    val pageIsList: Boolean = false
  )

  private val listSources = mapOf(
    "datahub-nasdaq"      to StockListSource(
      fileUrl     = "https://datahub.io/core/nasdaq-listings/r/nasdaq-listed.csv",
      fileType    = "csv"
    ),
    "datahub-nyse"        to StockListSource(
      fileUrl     = "https://datahub.io/core/nyse-other-listings/r/nyse-listed.csv",
      fileType    = "csv"
    ),
    "datahub-nyse-other"  to StockListSource(
      fileUrl     = "https://datahub.io/core/nyse-other-listings/r/other-listed.csv",
      fileType    = "csv"
    ),
    "thecse-cse"          to StockListSource(
      fileUrl     = "https://issuers.thecse.com/export-listings/xlsx?f={}",
      fileType    = "xlsx"
    ),
    "fmp-all"             to StockListSource(
      fileUrl     = "https://financialmodelingprep.com/api/v3/stock/list?apikey=",
      pageIsList  = true
    )
  )
}