/* Author: Green Apple
 * Date Created: August 14, 2025
 * 
 * Notes:
 */

package net.greenapple.arrestwt.data

// ====== IMPORTS ======
import net.greenapple.arrestwt.util.paths.*

// ====== DATA PATHS ======
class AssetPaths {
  companion object {
    val data:   String  = "user"
    val images: String  = "images"

    val accounts: String = "accounts"
    val cards:    String = "cards"
    val orgs:     String = "orgs"
    val people:   String = "people"
    val tags:     String = "tags"
    
    val accountsPath: String = accounts.inData()
    val cardsPath:    String = cards.inData()
    val orgsPath:     String = orgs.inData()
    val peoplePath:   String = people.inData()
    val tagsPath:     String = tags.inData()

    val accountImagesPath: String = images.inAccounts()
    val cardImagesPath:    String = images.inCards()
    val orgImagesPath:     String = images.inOrgs()
    val personImagesPath:  String = images.inPeople()
    val tagImagesPath:     String = images.inTags()
  }
}