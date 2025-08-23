plugins {
  id    ("com.android.application")
  id    ("org.jetbrains.kotlin.android")  version "1.9.22"
  kotlin("plugin.serialization")          version "1.9.22"
}

android {
  namespace   = "net.greenapple.arrestwt"
  compileSdk  = 34

  buildFeatures {
    compose = true
  }

  defaultConfig {
    applicationId = "net.greenapple.arrestwt"
    minSdk        = 26
    targetSdk     = 34
    versionCode   = 1
    versionName   = "1.0"
  }

  signingConfigs {
    getByName("debug") {
      storeFile     = file("${System.getProperty("user.home")}/.android/debug.keystore")
      storePassword = "android"
      keyAlias      = "androiddebugkey"
      keyPassword   = "android"
    }
  }

  buildTypes {
    getByName("debug") {
      signingConfig = signingConfigs.getByName("debug")
    }
    getByName("release") {
      signingConfig = signingConfigs.getByName("debug")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  kotlinOptions {
    jvmTarget = "11"
  }

  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.10"
  }
}

dependencies {
  implementation("androidx.core:core-ktx:1.13.0")
  implementation("androidx.appcompat:appcompat:1.7.0")
  implementation("androidx.activity:activity-compose:1.9.0")
  implementation("androidx.compose.ui:ui:1.6.7")
  implementation("androidx.compose.material3:material3:1.2.1")
  implementation("androidx.compose.material:material-icons-extended")
  implementation("androidx.compose.ui:ui-tooling-preview:1.6.7")
  implementation("androidx.navigation:navigation-compose:2.8.7")

  /* === Store Persistent Data */
  implementation("androidx.datastore:datastore-preferences:1.0.0")  /* General data */
  implementation("androidx.security:security-crypto:1.1.0")         /* Secure data */

  // Parse JSON
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

  // Run background tasks
  implementation("androidx.work:work-runtime-ktx:2.9.0")

  // HTTP client
  implementation("com.squareup.okhttp3:okhttp:4.12.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}