/* Author: Green Apple
 * Date Created: August 17, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.util.data

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.AssetPaths
import net.greenapple.arrestwt.data.type.AccountData
import net.greenapple.arrestwt.data.type.CardData
import net.greenapple.arrestwt.data.type.OrgData
import net.greenapple.arrestwt.data.type.PersonData
import net.greenapple.arrestwt.util.paths.*
import android.content.Context
import android.util.Log
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.IOException
import kotlin.collections.forEach

/* ====== Return T From JSON */
inline fun <reified T> String.getData(
  context:  Context
): T? {

  /* === Get the JSON as T */
  return try {

    /* --- Get JSON as String */
    val jsonStr = context.assets
      .open(this)
      .bufferedReader()
      .use { it.readText() }

    /* --- Decode JSON into T */
    Json { ignoreUnknownKeys = true }
      .decodeFromString<T>(jsonStr)
    
  /* === I/O Exception */
  } catch (e: IOException) {
    Log.e("DataUtils", "I/O Exception on $this")
    e.printStackTrace()
    null

  /* === Serialization Exception */
  } catch (e: SerializationException) {
    Log.e("DataUtils", "Serialization Exception on $this")
    e.printStackTrace()
    null
  }
}

/* ====== Return T From All JSONs in Directory */
inline fun <reified T> String.getAllData(
  context: Context
): List<T>? {

  /* === Normalize directories */
  val dir = this.trim('/')

  /* === Get JSONs as T */
  return try {

    /* --- Get JSON list of type T */
    context.assets
      .list(dir)
      .orEmpty()
      .asSequence()
      .filter { it.endsWith(".json") }
      .mapNotNull { file -> "$dir/$file".getData<T>(context) }
      .toList()
    
  /* === I/O Exception */
  } catch (e: IOException) {
    Log.e("DataUtils", "(getAllData) I/O Exception on $this")
    e.printStackTrace()
    null
  }
}

/* ====== Open and Return an Icon Image */
@Composable
fun String.getImage(
  context:      Context,
  path:         String  = this,
  conditional:  Boolean = true
): ImageBitmap? {

  /* === Get Image or Null */
  val image: ImageBitmap? by produceState<ImageBitmap?>(
    initialValue = null,
    path
  ) {

    /* --- If the condition passes, safely try to open and store the image */
    value = if (conditional) {
      try {
        withContext(Dispatchers.IO) {
          context.assets
            .open(path)
            .use { BitmapFactory.decodeStream(it)?.asImageBitmap() }
        }
      
      } catch (e: IOException) {
        Log.e("DataUtils", "I/O Exception on $path", e)
        e.printStackTrace()
        null
      }

    /* --- If there is no image or the condion does not pass, return null */
    } else null
  }
  
  return image
}

/* === Use id String to get Data of Types */
fun String.getAccount(context: Context):  AccountData?  = this.asAccountName().inAccounts().getData(context)
fun String.getCard(context: Context):     CardData?     = this.asCardName().inCards().getData(context)
fun String.getOrg(context: Context):      OrgData?      = this.asOrgName().inOrgs().getData(context)
fun String.getPerson(context: Context):   PersonData?   = this.asPersonName().inPeople().getData(context)
fun String.getTag(context: Context):      PersonData?   = this.asTagName().inTags().getData(context)

/* ====== Get Material Icon Using Icon Map */
val iconMap: Map<String, ImageVector> = mapOf(
  "Bookmark"  to Icons.Filled.Bookmark,
  "Dining"    to Icons.Filled.Dining
)
fun String.getMaterialIcon(): ImageVector = iconMap[this] ?: Icons.Filled.QuestionMark