/* Author: Green Apple
 * Date Created: August 18, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.util.paths

// ====== IMPORTS ======
import java.net.URLDecoder.decode
import java.text.Normalizer
import kotlin.text.startsWith

// ====== FUNCTIONS ======
private fun String?.normalizeDecodeUtf8(): String? {

  /* --- Reject null strings */
  if (this == null) return null

  /* --- Force UTF_8 */
  var str: String = try { decode(this, "UTF-8") } catch (e: IllegalArgumentException) { return null }

  /* --- Normalize text */
  return Normalizer.normalize(str, Normalizer.Form.NFC)
}

val VALID_CHAR_REGEX: Regex = Regex("^[A-Za-z0-9._-]{1,128}$")

/* ====== Ensure Strings are Safe to be Used as Paths */
fun String?.toSafePath(): String? {
  val str: String? = this.normalizeDecodeUtf8()

  /* --- Reject invalid strings and patterns */
  if (str.isNullOrBlank()             /* Is blank/"" or null */
    || str.length > 128               /* Is too long */
    || !str.matches(VALID_CHAR_REGEX) /* Has invalid character */
    || str == "."                     /* Is . dotfile */
    || str == ".."                    /* Is .. dotfile */
    || str.contains('/')              /* Indicates directory with / */
    || str.contains('\\')             /* Indicates directory with \ */
    || str.contains('\u0000')         /* Contains null character */
  ) return null

  return str
}

fun String?.toSafeAssetPath(): String? {
  val str: String? = this.normalizeDecodeUtf8()

  /* --- Reject invalid strings and patterns */
  if (str.isNullOrBlank()             /* Is blank/"" or null */
    || str.length > 256               /* Is too long */
    || str == "."                     /* Is . dotfile */
    || str == ".."                    /* Is .. dotfile */
    || str.startsWith('/')            /* Is non-relative path */
    || str.contains("\\")             /* Contains \ character */
    || str.contains('\r')             /* Contains \r end of line marker */
    || str.contains('\n')             /* Contains \n end of line marker */
    || str.contains('\u0000')         /* Contains null character */
    || str.contains("//")             /* Contains empty segment (/) */
  ) return null

  val segments = str.split('/')
  if (segments.any { it.isBlank() || it == "." || it == ".." || !it.matches(VALID_CHAR_REGEX) } ) return null

  return str
}

object filePaths {
  val data:     String  = "user"
  val defaults: String  = "defaults"
  val images:   String  = "images"

  val accounts:     String = "accounts"
  val cards:        String = "cards"
  val orgs:         String = "orgs"
  val people:       String = "people"
  val tags:         String = "tags"
  val themes:       String = "themes"
  val transactions: String = "transactions"
  
  val accountsPath:     String = accounts.inData()!!
  val cardsPath:        String = cards.inData()!!
  val orgsPath:         String = orgs.inData()!!
  val peoplePath:       String = people.inData()!!
  val tagsPath:         String = tags.inData()!!
  val themesPath:       String = themes.inData()!!
  val transactionsPath: String = transactions.inData()!!

  val themesDefaultPath:  String = themes.inDefaults()!!

  val accountImagesPath: String = images.inAccounts()!!
  val cardImagesPath:    String = images.inCards()!!
  val orgImagesPath:     String = images.inOrgs()!!
  val personImagesPath:  String = images.inPeople()!!
  val tagImagesPath:     String = images.inTags()!!
}

fun String?.inPath(dir: String):  String? = this.toSafePath()?.let { "$dir/$it" }

fun String?.inData():             String? = this.inPath(filePaths.data)
fun String?.inDefaults():         String? = this.inPath(filePaths.defaults)

fun String?.inAccounts():         String? = this.inPath(filePaths.accountsPath)
fun String?.inCards():            String? = this.inPath(filePaths.cardsPath)
fun String?.inOrgs():             String? = this.inPath(filePaths.orgsPath)
fun String?.inPeople():           String? = this.inPath(filePaths.peoplePath)
fun String?.inTags():             String? = this.inPath(filePaths.tagsPath)
fun String?.inThemes():           String? = this.inPath(filePaths.themesPath)
fun String?.inTransactions():     String? = this.inPath(filePaths.transactionsPath)

fun String?.asAccountName():      String? = this.toSafePath()?.let { "$it.account.json" }
fun String?.asCardName():         String? = this.toSafePath()?.let { "$it.card.json" }
fun String?.asOrgName():          String? = this.toSafePath()?.let { "$it.org.json" }
fun String?.asPersonName():       String? = this.toSafePath()?.let { "$it.person.json" }
fun String?.asTagName():          String? = this.toSafePath()?.let { "$it.tag.json" }
fun String?.asTransactionName():  String? = this.toSafePath()?.let { "$it.transaction.json" }

fun String?.inAccountImages():    String? = this.inPath(filePaths.accountImagesPath)
fun String?.inCardImages():       String? = this.inPath(filePaths.cardImagesPath)
fun String?.inOrgImages():        String? = this.inPath(filePaths.orgImagesPath)
fun String?.inPersonImages():     String? = this.inPath(filePaths.personImagesPath)
fun String?.inTagImages():        String? = this.inPath(filePaths.tagImagesPath)

fun String?.asAccountImageName(): String? = this.toSafePath()?.let { "$it.account.png" }
fun String?.asCardImageName():    String? = this.toSafePath()?.let { "$it.card.png" }
fun String?.asOrgImageName():     String? = this.toSafePath()?.let { "$it.org.png" }
fun String?.asPersonImageName():  String? = this.toSafePath()?.let { "$it.person.png" }
fun String?.asTagImageName():     String? = this.toSafePath()?.let { "$it.tag.png" }