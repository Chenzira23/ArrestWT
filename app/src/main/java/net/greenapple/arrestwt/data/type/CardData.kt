/* Author: Green Apple
 * Date Created: August 13, 2025
 * 
 * Other Dates:
 * > Refactored: August 15, 2025
 * 
 * Notes: Represents a card and its data
 */

package net.greenapple.arrestwt.data.type

// ====== IMPORTS ======
import kotlinx.serialization.Serializable

// ====== CARD DATA ======
/* uuid:
 * Unique identifier for every card.
 * 
 * name:
 * The name of a card. Used to differentiate between cards at a glance.
 * Names do not have to be unique.
 * 
 * accounts:
 * The uuid of accounts the card can interact with. This is a list since
 * one card can be linked to multiple accounts such as a chequings and a
 * savings.
 * 
 * TODO:
 * - Append number to non-uniquely named cards
 */
@Serializable
data class CardData(
  val uuid:       String,
  val name:       String,
  val hasImage:   Boolean         = false,
  val accounts:   List<String?>?,
  val tags:       List<String?>?  = null
)