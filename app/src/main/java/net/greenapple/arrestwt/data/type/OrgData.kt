/* Author: Green Apple
 * Date Created: August 15, 2025
 * 
 * Notes: Represents an organization and its data
 */

package net.greenapple.arrestwt.data.type

// ====== IMPORTS ======
import kotlinx.serialization.Serializable

// ====== MARKET DATA ======
/* id:
 * Identifier for an org. Orgs are not given truly unique identifiers to make
 * future imports more consistent.
 * Org identifiers are also the fallback "name."
 * 
 * copy:
 * If this org is a synonym for another. For example, an id "pizzaplace" could be a
 * synonym for "pizza-place." If this org is a copy, it will copy everything except
 * for the id of the primary org (in this case "pizza-place").
 * 
 * name:
 * The name of the org. The name exists as to allow uppercase letters and other
 * non-standard characters in the org's id. For example an id "johns-pub" could have
 * a name "John's Pub."
 * 
 * hasImage:
 * If the org has an image.
 * 
 * icon:
 * The icon used for the org if there is one. */
@Serializable
data class OrgData(
  val id:       String,
  val copy:     String?         = null,
  val name:     String?         = null,
  val hasImage: Boolean         = false,
  val icon:     String?         = null,
  val tags:     List<String?>?  = null
)