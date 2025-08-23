/* Author: Green Apple
 * Date Created: August 18, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.util.paths

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.AssetPaths

// ====== FUNCTIONS ======
fun String.inPath(dir: String):   String = "$dir/$this"

fun String.inData():                String = this.inPath(AssetPaths.data)

fun String.inAccounts():            String = this.inPath(AssetPaths.accountsPath)
fun String.inCards():               String = this.inPath(AssetPaths.cardsPath)
fun String.inOrgs():                String = this.inPath(AssetPaths.orgsPath)
fun String.inPeople():              String = this.inPath(AssetPaths.peoplePath)
fun String.inTags():                String = this.inPath(AssetPaths.tagsPath)

fun String.asAccountName():         String = "$this.account.json"
fun String.asCardName():            String = "$this.card.json"
fun String.asOrgName():             String = "$this.org.json"
fun String.asPersonName():          String = "$this.person.json"
fun String.asTagName():             String = "$this.tag.json"

fun String.inAccountImages():       String = this.inPath(AssetPaths.accountImagesPath)
fun String.inCardImages():          String = this.inPath(AssetPaths.cardImagesPath)
fun String.inOrgImages():           String = this.inPath(AssetPaths.orgImagesPath)
fun String.inPersonImages():        String = this.inPath(AssetPaths.personImagesPath)
fun String.inTagImages():           String = this.inPath(AssetPaths.tagImagesPath)

fun String.asAccountImageName():    String = "$this.account.png"
fun String.asCardImageName():       String = "$this.card.png"
fun String.asOrgImageName():        String = "$this.org.png"
fun String.asPersonImageName():     String = "$this.person.png"
fun String.asTagImageName():        String = "$this.tag.png"