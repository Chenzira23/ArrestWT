/* Author: Green Apple
 * Date Created: August 15, 2025
 * 
 * Notes: Represents a person and its data
 */

package net.greenapple.arrestwt.data.type

// ====== IMPORTS ======
import kotlinx.serialization.Serializable

// ====== PERSON DATA ======
/* uuid:
 * Unique identifier for every person.
 * 
 * name:
 * The name of the person.
 * 
 * hasImage:
 * If the person has an image. */
@Serializable
data class PersonData(
  val uuid:     String,
  val name:     String,
  val hasImage: Boolean         = false,
  val tags:     List<String?>?  = null
)