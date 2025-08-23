/* Author: Green Apple
 * Date Created: August 11, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.util.serialization

// ====== IMPORTS ======
import androidx.compose.ui.graphics.Color
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.SerializationException

// ====== COLOR HEX AS STRING SERIALIZER ======
object ColorHexAsStringSerializer: KSerializer<Color> {
  
  /* ====== Override Serial Descriptor for Referencing */
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("ColorHexAsString", PrimitiveKind.STRING)

  /* ====== Override Encoding to String as Color Hex */
  override fun serialize(
    encoder: Encoder,
    value: Color
  ) {
    val a = (value.alpha * 255).toInt().coerceIn(0, 255)
    val r = (value.red * 255).toInt().coerceIn(0, 255)
    val g = (value.green * 255).toInt().coerceIn(0, 255)
    val b = (value.blue * 255).toInt().coerceIn(0, 255)

    encoder.encodeString(String.format("#%02X%02X%02X%02X", a, r, g, b))
  }

  /* ====== Override Decoding String as Color Hex */
  override fun deserialize(
    decoder: Decoder
  ): Color {
    val hexStr  = decoder.decodeString().trim()
    val isHex   = Regex("^#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{8})$").matches(hexStr)
    if (!isHex) throw SerializationException("Invalid color hex: '$hexStr'")
    val argb    = android.graphics.Color.parseColor(hexStr)
    
    return Color(argb)
  }
}