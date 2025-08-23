/* Author: Green Apple
 * Date Created: August 5, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt.util

// ====== IMPORTS ======
import net.greenapple.arrestwt.data.type.ThemeData
import android.content.Context
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.SerializationException
import java.io.IOException

// ====== THEME UTILS ======
object ThemeUtils {

  private val themesPath = "defaults/themes"

  /* ====== Return Themes From JSONs */
  fun loadThemeFromJson(
    context:  Context,
    fileName: String
  ): ThemeData? {

    /* === Try to Laod JSON String From File */
    return try {
      val jsonStr = context.assets
        .open("$themesPath/$fileName")
        .bufferedReader()
        .use {
          it.readText()
        }
      
      Json.decodeFromString<ThemeData>(jsonStr)

    } catch (e: IOException) {
      e.printStackTrace()
      null
    } catch (e: SerializationException) {
      e.printStackTrace()
      null
    }
  }

  /* ====== Return All Default JSON Themes as Map */
  fun listDefaultJsonThemes(
    context: Context
  ): Map<String, String> {

    /* --- Define assets directories */
    val assetManager  = context.assets
    val themeFiles    = assetManager
      .list("$themesPath")
      ?.filter {
        it.endsWith(".json")
      }?: return emptyMap()

    /* === Return Theme And Format Name */
    return themeFiles.mapNotNull { fileName ->
      try {

        /* --- Get theme data */
        val json = assetManager
        .open("$themesPath/$fileName")
        .bufferedReader()
        .use {
          it.readText()
        }

        /* --- Get theme name */
        val theme = Json.decodeFromString<ThemeData>(json)
        (theme.name ?: "Unnamed Theme") to fileName

      } catch (e: Exception) {
        e.printStackTrace()
        null
      }
    }.toMap()
  }
}