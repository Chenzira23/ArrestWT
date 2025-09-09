/* Author: Green Apple
 * Date Created: August 17, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.util.data

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.type.AccountData
import net.greenapple.arrestwt.data.type.CardData
import net.greenapple.arrestwt.data.type.OrgData
import net.greenapple.arrestwt.data.type.PersonData
import net.greenapple.arrestwt.data.type.TagData
import net.greenapple.arrestwt.data.type.ThemeData
import net.greenapple.arrestwt.data.type.TransactionData
import net.greenapple.arrestwt.util.paths.*
import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Color
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
import java.security.MessageDigest
import kotlin.collections.forEach
import kotlin.text.startsWith

/* ====== GETTING DATA ====== */
fun String?.getSafeFile(
  dirFile: File
): File? {

  if (this == null) return null

  val dirCanonical  = try { dirFile.canonicalFile } catch (e: IOException) { return null }
  var file          = File(dirFile, this)
  val fileCanonical = try { file.canonicalFile } catch (e: IOException) { return null }

  Log.i("DataUtils", "(getSafeFile) canonDir: ${dirCanonical.path}, canonFile: ${fileCanonical.path}")

  return if (fileCanonical.path == dirCanonical.path || fileCanonical.path.startsWith(dirCanonical.path + File.separator)) fileCanonical else null
}

/* ====== Return T From JSON in files/ */
inline fun <reified T> String.getData(
  context:  Context
): T? {

  /* === Get File or Null if not safe */
  val file: File = this.getSafeFile(context.filesDir)
    ?: run {
      Log.w("DataUtils", "(getData) Rejected path: $this")
      return null
    }

  /* === Get the JSON as T */
  return try {

    /* --- Get JSON as String */
    val jsonStr = file
      .bufferedReader()
      .use { it.readText() }

    /* --- Decode JSON into T */
    Json { ignoreUnknownKeys = true }
      .decodeFromString<T>(jsonStr)
    
  /* === I/O Exception */
  } catch (e: IOException) {
    Log.e("DataUtils", "(getData) I/O Exception on $this", e)
    null

  /* === Serialization Exception */
  } catch (e: SerializationException) {
    Log.e("DataUtils", "(getData) Serialization Exception on $this", e)
    null
  }
}

/* ====== Return T From JSON in assets/ */
inline fun <reified T> String.getAssetData(
  context: Context
): T? {

  /* === Determine if the Path is Safe to Open */
  val path: String = this.toSafeAssetPath()
    ?: run {
      Log.w("DataUtils", "(getAssetData) Rejected asset path: $this")
      return null
    }

  return try {
    /* --- Get JSON as String */
    val jsonStr = context.assets
      .open(path)
      .bufferedReader()
      .use {
        it.readText()
      }

    /* --- Decode JSON into T */
    Json { ignoreUnknownKeys = true }
      .decodeFromString<T>(jsonStr)

  /* === I/O Exception */
  } catch (e: IOException) {
    Log.e("DataUtils", "(getAssetData) I/O Exception on $this", e)
    null

  /* === Serialization Exception */
  } catch (e: SerializationException) {
    Log.e("DataUtils", "(getAssetData) Serialization Exception on $this", e)
    null
  }
}

/* ====== Return T From All JSONs in files/Directory */
/* TODO: Variable/Value names are ambiguous and need to be updated */
inline fun <reified T> String.getAllData(
  context:  Context
): List<T>? {

  /* === Open the directory safely */
  val dir = this.getSafeFile(context.filesDir)
    ?: run {
      Log.w("DataUtils", "(getAllData) Rejected path: $this")
      return null
    }

  /* === Open the file as a directory if it exists */
  if (!dir.exists() || !dir.isDirectory) {
    Log.e("DataUtils", "(getAllData) Cannot access as directory: ${dir.path}")
    return null
  }

  val baseDirCanonical: File = context.filesDir.canonicalFile

  /* === Get JSONs as T */
  return dir.listFiles { entry ->
    entry.isFile && entry.extension.equals("json", ignoreCase = true)
  }
    ?.asSequence()
    ?.mapNotNull {
      try { it.canonicalFile.toRelativeString(baseDirCanonical).getData<T>(context) }

      /* === Illegal Argument Exception */
      /* Path is not in parent */
      catch (e: IllegalArgumentException) {
        Log.w("DataUtils", "(getAllData) Skipping Illegal Argument Exception on ${it.path}")
        null

      /* === I/O Exception */
      } catch (e: IOException) {
        Log.w("DataUtils", "(getAllData) Skipping I/O Exception on ${it.path}")
        null
      }
    }
    ?.toList()
}

/* ====== Return T From All JSONs in assets/Directory */
inline fun <reified T> String.getAllAssetData(
  context: Context
): List<T>? {

  /* === Determine if the Path is Safe to Open */
  val path: String = this.toSafeAssetPath()
    ?: run {
      Log.w("DataUtils", "(getAssetData) Rejected asset path: $this")
      return null
    }

  val dirFiles = context.assets
    .list(path)
    ?.filter {
      it.endsWith(".json")
    }?: return emptyList()

  /* === Get JSONs as List<T> */
  return dirFiles.mapNotNull { "$path/$it".getAssetData<T>(context) }.toList()
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
    path,
    conditional
  ) {

    /* --- If the condition passes, safely try to open and store the image */
    value = if (conditional) {
      val safePath: String? = path.toSafeAssetPath()

      if (safePath == null) {
        Log.w("DataUtils", "(getImage) Rejected path: $path")
        null

      } else {
        try {
          withContext(Dispatchers.IO) {
            context.assets
              .open(safePath)
              .use { BitmapFactory.decodeStream(it)?.asImageBitmap() }
          }
        
        } catch (e: IOException) {
          Log.e("DataUtils", "(getImage) I/O Exception on $safePath", e)
          null
        }
      }

    /* --- If there is no image or the condion does not pass, return null */
    } else null
  }
  
  return image
}

/* === Use String to Get Data of Types */
fun String.getAccount(context: Context):      AccountData?      = this.asAccountName().inAccounts()?.getData(context)
fun String.getCard(context: Context):         CardData?         = this.asCardName().inCards()?.getData(context)
fun String.getOrg(context: Context):          OrgData?          = this.asOrgName().inOrgs()?.getData(context)
fun String.getPerson(context: Context):       PersonData?       = this.asPersonName().inPeople()?.getData(context)
fun String.getTag(context: Context):          TagData?          = this.asTagName().inTags()?.getData(context)
fun String.getTheme(context: Context):        ThemeData?        = "$this.json".inThemes()?.getData(context)
fun String.getTransaction(context: Context):  TransactionData?  = this.asTransactionName().inTransactions()?.getData(context)

/* === Use String to get All Data in Directory of Types */
fun getDefaultThemes(context: Context): List<ThemeData> = filePaths.themesDefaultPath.getAllAssetData<ThemeData>(context) ?: emptyList()
fun getUserThemes(context: Context):    List<ThemeData> = filePaths.themesPath.getAllData<ThemeData>(context) ?: emptyList()
fun getAllThemes(context: Context):     List<ThemeData> = getDefaultThemes(context) + getUserThemes(context)

/* === Get Material Icon Using Icon Map */
val iconMap: Map<String, ImageVector> = mapOf(
  "Bookmark"  to Icons.Filled.Bookmark,
  "Dining"    to Icons.Filled.Dining
)
fun String.getMaterialIcon(): ImageVector = iconMap[this] ?: Icons.Filled.QuestionMark

/* ====== SETTING DATA ====== */
fun CreateFile(
  context:  Context,
  path:     String?,
  data:     String
): Boolean {

  val file: File = path.getSafeFile(context.filesDir)
    ?: run {
      Log.w("DataUtils", "(CreateFile) Rejected path: $path")
      return false
    }

  return try {
    Log.i("DataUtils", "(CreateFile) Creating file: ${file.path}")

    file.parentFile?.mkdirs()
    file.writeText(data)
    true

  } catch (e: IOException) {
    Log.e("DataUtils", "(CreateFile) I/O Exception on $path")
    false
  }
}

fun String.deleteFile(
  context:  Context,
): Boolean {

  val file: File = this.getSafeFile(context.filesDir)
    ?: run {
      Log.w("DataUtils", "(deleteFile) Rejected path: $this")
      return false
    }
  
  return try {
    file.exists() && file.delete()

  } catch (e: Exception) {
    Log.e("DataUtils", "(deleteFile) Failed to delete $this", e)
    false
  }
}

inline fun <reified T> String.editData(
  context:    Context,
  transform:  (T) -> T
): Boolean {

  val file: File = this.getSafeFile(context.filesDir)
    ?: run {
      Log.w("DataUtils", "(editData) Rejected path: $this")
      return false
    }
  val fileTmp: File = File(file.parentFile, "${file.name}.tmp")

  val existing          = this.getData<T>(context) ?: return false
  val transformed       = transform(existing)

  return try {
    fileTmp.writeText(Json.encodeToString(transformed))

    if (!fileTmp.renameTo(file)) {
      file.writeText(Json.encodeToString(transformed))
      fileTmp.delete()
    }
    true

  } catch (e: IOException) {
    Log.e("DataUtils", "(editData) I/O Exception on $this", e)
    false
  }
}

/* === Use String to Delete Data of Types */
fun String.deleteAccount(context: Context):      Boolean? = this.asAccountName().inAccounts()?.deleteFile(context)
fun String.deleteCard(context: Context):         Boolean? = this.asCardName().inCards()?.deleteFile(context)
fun String.deleteOrg(context: Context):          Boolean? = this.asOrgName().inOrgs()?.deleteFile(context)
fun String.deletePerson(context: Context):       Boolean? = this.asPersonName().inPeople()?.deleteFile(context)
fun String.deleteTag(context: Context):          Boolean? = this.asTagName().inTags()?.deleteFile(context)
fun String.deleteTheme(context: Context):        Boolean? = "$this.json".inThemes()?.deleteFile(context)
fun String.deleteTransaction(context: Context):  Boolean? = this.asTransactionName().inTransactions()?.deleteFile(context)

/* === Use String to Edit Data of Types */
fun String.editAccount(context: Context, transform: (AccountData) -> AccountData):              Boolean?
  = this.asAccountName().inAccounts()?.editData(context, transform)
fun String.editCard(context: Context, transform: (CardData) -> CardData):                       Boolean?
  = this.asCardName().inCards()?.editData(context, transform)
fun String.editOrg(context: Context, transform: (OrgData) -> OrgData):                          Boolean?
  = this.asOrgName().inOrgs()?.editData(context, transform)
fun String.editPerson(context: Context, transform: (PersonData) -> PersonData):                 Boolean?
  = this.asPersonName().inPeople()?.editData(context, transform)
fun String.editTag(context: Context, transform: (TagData) -> TagData):                          Boolean?
  = this.asTagName().inTags()?.editData(context, transform)
fun String.editTheme(context: Context, transform: (ThemeData) -> ThemeData):                    Boolean?
  = "$this.json".inThemes()?.editData(context, transform)
fun String.editTransaction(context: Context, transform: (TransactionData) -> TransactionData):  Boolean?
  = this.asTransactionName().inTransactions()?.editData(context, transform)

/* ====== DETERMINIG DATA ====== */
/* ====== Return a Color Generated by String */
fun String.generateColor(
  saturation: Float = 0.55f,
  lightness:  Float = 0.50f,
  alpha:      Float = 1f
): Color {

  val lowerStr: String = this.lowercase()

  /* --- Get hash of String */
  val hash = MessageDigest.getInstance("SHA-1")
    .digest(lowerStr.toByteArray(Charsets.UTF_8))

  /* --- Get hue */
  val n       = (hash[0].toUByte().toInt() shl 16)  or
                (hash[1].toUByte().toInt() shl 8)   or
                hash[2].toUByte().toInt()
    val hue     = (n % 360).toFloat()

  /* --- Get color */
    return Color.hsl(
      hue         = hue,
      saturation  = saturation.coerceIn(0f, 1f),
      lightness   = lightness.coerceIn(0f, 1f),
      alpha       = alpha
    )
}