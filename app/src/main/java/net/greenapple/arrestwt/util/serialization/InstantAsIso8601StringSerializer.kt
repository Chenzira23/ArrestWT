/* Author: Green Apple
 * Date Created: August 11, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.util.serialization

// ====== IMPORTS ======
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant

// ====== INSTANT AS ISO-8601 STRING SERIALIZER ======
object InstantAsIso8601StringSerializer: KSerializer<Instant> {

  /* ====== Override Serial Descriptor for Referencing */
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("InstantAsIso8601String", PrimitiveKind.STRING)

  /* ====== Override Encoding to String as ISO-8601 */
  override fun serialize(
    encoder: Encoder,
    value: Instant
  ) {
    encoder.encodeString(value.toString())
  }

  /* ====== Override Decoding String as ISO-8601 */
  override fun deserialize(
    decoder: Decoder
  ): Instant {
    return Instant.parse(decoder.decodeString())
  }
}