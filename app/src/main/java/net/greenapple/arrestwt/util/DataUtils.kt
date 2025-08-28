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
import net.greenapple.arrestwt.data.type.TagData
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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.File
import kotlin.collections.forEach

/* ====== GETTING DATA ====== */
/* ====== Return T From JSON */
inline fun <reified T> String.getData(
  context:  Context
): T? {

  /* === Get the JSON as T */
  return try {

    val file: File = File(context.filesDir, this)

    /* --- Get JSON as String */
    val jsonStr = file
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

    val file: File = File(context.filesDir, dir)

    if (!file.exists() || !file.isDirectory()) {
      Log.e("DataUtils", "(getAllData) Cannot access $this")
      null

    /* --- Get JSON list of type T */
    } else {
      file.listFiles { entry ->
        entry.isFile && entry.extension.equals("json", ignoreCase = true) }
        ?.asSequence()
        ?.mapNotNull { "$dir/${it.name}".getData<T>(context) }
        ?.toList()
    }
    
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
fun String.getTag(context: Context):      TagData?      = this.asTagName().inTags().getData(context)

/* ====== Get Material Icon Using Icon Map */
val iconMap: Map<String, ImageVector> = mapOf(
  "Bookmark"  to Icons.Filled.Bookmark,
  "Dining"    to Icons.Filled.Dining
)
fun String.getMaterialIcon(): ImageVector = iconMap[this] ?: Icons.Filled.QuestionMark

/* ====== SETTING DATA ====== */
fun CreateFile(
  context:  Context,
  path:     String,
  data:     String
) {

  val file: File = File(context.filesDir, path)
  file.parentFile?.mkdirs()
  file.writeText(data)
}

fun String.deleteFile(
  context:  Context,
): Boolean {
  
  return try {
    val targetFile: File = File(context.filesDir, this)
    
    if (targetFile.exists()) targetFile.delete()
    else false

  } catch (e: Exception) {
    Log.e("DataUtils", "(deleteFile) Failed to delete $this", e)
    e.printStackTrace()
    false
  }
}

inline fun <reified T> String.editData(
  context:    Context,
  transform:  (T) -> T
): Boolean {

  val targetFile: File  = File(context.filesDir, this)
  val tmpFile:    File  = File(targetFile.parentFile, "${targetFile.name}.tmp")
  val existing          = this.getData<T>(context) ?: return false
  val transformed       = transform(existing)

  return try {
    tmpFile.writeText(Json.encodeToString(transformed))

    if (!tmpFile.renameTo(targetFile)) {
      targetFile.writeText(Json.encodeToString(transformed))
      tmpFile.delete()
    }
    true

  } catch (e: IOException) {
    Log.e("DataUtils", "(editData) I/O Exception on $this", e)
    e.printStackTrace()
    false
  }
}