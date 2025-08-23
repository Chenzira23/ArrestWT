/* Author: Green Apple
 * Date Created: August 14, 2025
 * 
 * Notes: Represents an entity such as a person, org, etc.
 * Primarily used as a placeholder that defines if said entity
 * is a person, organization, etc. as well as the relevant
 * information needed to determine how it should be treated.
 */

package net.greenapple.arrestwt.data.type

// ====== IMPORTS ======
import kotlinx.serialization.Serializable

// ====== ENTITY DATA ======
/* id:
 * The identifier for any entity. For example, if the entity is a
 * person, this should be the person's uuid. If the entity was an
 * org, this would be that organization's id.
 * 
 * type:
 * The type of entity. Options are person and org.
 */
@Serializable
data class EntityData(
  val id: String,
  val type: String
)