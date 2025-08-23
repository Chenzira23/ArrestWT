/* Author: Green Apple
 * Date Created: August 9, 2025
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
import java.math.BigDecimal

// ====== BIG DECIMAL AS STRING SERIALIZER ======
object BigDecimalAsStringSerializer: KSerializer<BigDecimal> {

  /* ====== Override Serial Descriptor for Referencing */
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("BigDecimalAsString", PrimitiveKind.STRING)

  /* ====== Override Encoding to String as Big Decimal */
  override fun serialize(
    encoder: Encoder,
    value: BigDecimal
  ) {
    encoder.encodeString(value.toPlainString())
  }

  /* ====== Override Decoding String as Big Decimal */
  override fun deserialize(
    decoder: Decoder
  ): BigDecimal {
    return BigDecimal(decoder.decodeString())
  }
}