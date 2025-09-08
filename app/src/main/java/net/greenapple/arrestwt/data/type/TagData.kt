/* Author: Green Apple
 * Date Created: August 11, 2025
 * 
 * Other Dates:
 * > Refactored: August 15, 2025
 * 
 * Notes: Represents a tag and its data
 */

package net.greenapple.arrestwt.data.type

// ====== IMPORTS ======
import net.greenapple.arrestwt.util.data.generateColor
import net.greenapple.arrestwt.util.serialization.ColorHexAsStringSerializer
import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

// ====== TAG DATA ======
/* id:
 * Identifier for a tag. Tags are not given truly unique identifiers to make
 * future imports more consistent, as well as allowing for generated tag colors
 * to remain consistent across all devices.
 * Tag identifiers are also the fallback "name."
 * 
 * copy:
 * If this tag is a synonym for another. For example, an id "brunch" could be a
 * synonym for "breakfast." If this tag is a copy, it will copy everything except
 * for the id of the primary tag (in this case "breakfast").
 * 
 * name:
 * The name of the tag. This is the displayed text on a tag. The name exists as to
 * allow uppercase letters and other non-standard characters in the tag's id.
 * For example an id "johns-pub" could have a name "John's Pub."
 * 
 * hasImage:
 * If the supplied icon has an image. For:
 * true: The icon has an image and should be treated as such
 * false: The icon does not have an image, but a default icon or text if icon is null
 * 
 * icon:
 * The icon used for the tag if there is one.
 * 
 * iconShape:
 * The shape of the icon. Options are "circle", "square", and "rectangle"
 * All options should have the same height value as to prevent a tag from breaking any
 * formatted sections.
 * 
 * iconWithText:
 * If the icon should have the tag name next to it.
 * 
 * color:
 * The color of a tag. This will only change the outline / surface arround a tag.
 * 
 * effectiveColor:
 * The generated color of a tag based on its identifier. Intended to only be used if
 * the user has not defined it a color prior.
 * To prevent constant updates and parsing of the color, if there is no color assigned,
 * assign the tag JSON the generated color after its creation, then use the "color" value. */
@Serializable
data class TagData(
  val id:             String,
  val copy:           String?     = null,
  val name:           String?     = null,
  val hasImage:       Boolean     = false,
  val icon:           String?     = null,
  val iconShape:      String?     = null,
  val iconWithText:   Boolean     = false,
  @Serializable(with = ColorHexAsStringSerializer::class)
  val color:          Color?      = null
) {
  val effectiveColor: Color get() = color ?: id.generateColor()
}