/* Author: Green Apple
 * Date Created: August 1, 2025
 * 
 * Notes: 
 */

package net.greenapple.arrestwt

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.AppTheme
import net.greenapple.arrestwt.ui.NavRoute
import net.greenapple.arrestwt.ui.appearance.TransitionAnimationAppearance
import net.greenapple.arrestwt.ui.page.*
import net.greenapple.arrestwt.ui.viewmodel.*
import android.os.Bundle
import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions

// ====== MAIN CLASS ======
class MainActivity: ComponentActivity()
{
  /* ====== On App Create */
  override fun onCreate(
    savedInstanceState: Bundle?
  ) {
    super.onCreate(savedInstanceState)

    /* === Make App Edge-to-edge Layout */
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor     = Color.TRANSPARENT
    window.navigationBarColor = Color.TRANSPARENT

    /* === Set Content */
    setContent {
      val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(applicationContext)
      }

      val visibilityViewModel: VisibilityViewModel by viewModels { 
        VisibilityViewModelFactory(applicationContext)
      }

      /* --- Wrap app in theme */
      AppTheme(settingsViewModel) {

        /* --- Main app UI */
        MainAppCompose(
          visibilityViewModel = visibilityViewModel,
          settingsViewModel   = settingsViewModel
        )
      }
    }
  }

  /* ====== Create App UI */
  @Composable
  fun MainAppCompose(
    visibilityViewModel:  VisibilityViewModel,
    settingsViewModel:    SettingsViewModel
  ) {

    /* === Navigation Controller Values */
    val navController     = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute      = navBackStackEntry?.destination?.route
    val startRoute        by settingsViewModel
      .selectedStartingPageFlow
      .collectAsState(initial = NavRoute.Home.route)

    /* === Add Bottom Navigation Bar */
    Scaffold(
      contentWindowInsets = WindowInsets(0),
      bottomBar = {
        Column() {

          /* --- Add line above navigation bar */
          HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
            modifier = Modifier.fillMaxWidth()
          )

          /* --- Add navigation bar body */
          NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 0.dp
          ) {

            /* --- Add navigation bar items */
            NavRoute.bottomNavRoutes.forEach { item ->
              NavigationBarItem(

                /* Add each item's icon */
                icon = {
                  Icon(
                    item.icon,
                    contentDescription = item.label
                  )
                },
                selected = currentRoute == item.route,

                /* Move route when clicked on bottom bar */
                onClick = {
                  if (currentRoute != item.route) {
                    navController.navigate(item.route) {
                      popUpTo(NavRoute.Home.route) { inclusive = false }
                      launchSingleTop = true
                    }
                  }
                }
              )
            }
          }
        }
      }
    ) { padding ->

      /* === Add Paadbges */
      NavHost(
        navController     = navController,
        startDestination  = NavRoute.Home.route,
        enterTransition   = { TransitionAnimationAppearance.enter(this, NavRoute.bottomNavRoutes) },
        exitTransition    = { TransitionAnimationAppearance.exit(this, NavRoute.bottomNavRoutes) },
        modifier          = Modifier
          .padding(padding),
      ) {

        /* --- Navigation settings for tabs ontop of primary pages */
        val topTabOptions = navOptions {
          launchSingleTop = true
          restoreState    = true
        }

        /* --- Navigation button routes */
        val onBack: () -> Unit    = { navController.navigateUp() } 
        val onNotificationsClick  = { navController.navigate(NavRoute.Notifications.route, topTabOptions) }
        val onSettingsClick       = { navController.navigate(NavRoute.Settings.route, topTabOptions) }
        val onAccountsClick       = { navController.navigate(NavRoute.Accounts.route, topTabOptions)}
        val onTagsClick           = { navController.navigate(NavRoute.Tags.route, topTabOptions)}
        val onAddTransactionClick = { navController.navigate(NavRoute.AddTransaction.route, topTabOptions) }
        val onAddAccountClick     = { navController.navigate(NavRoute.AddAccount.route, topTabOptions) }
        val onAddTagsClick        = { navController.navigate(NavRoute.AddTag.route, topTabOptions) }
        val onEducateClick        = { navController.navigate(NavRoute.Educate.route, topTabOptions) }

        /* --- Add pages */
        /* Home related */
        composable(NavRoute.Home.route) { 
          HomePage(
            visibilityViewModel   = visibilityViewModel,
            onNotificationsClick  = onNotificationsClick,
            onSettingsClick       = onSettingsClick
          ) 
        }
        composable(NavRoute.Notifications.route)  { NotificationsPage(onBack) }
        composable(NavRoute.Settings.route)       { SettingsPage(settingsViewModel, onBack) }

        /* Transaction related */
        composable(NavRoute.Transactions.route)   {
          TransactionsPage(
            visibilityViewModel   = visibilityViewModel,
            onAccountsClick       = onAccountsClick,
            onTagsClick           = onTagsClick,
            onAddTransactionClick = onAddTransactionClick,
            onAddAccountClick     = onAddAccountClick,
            onAddTagsClick        = onAddTagsClick
          )
        }
        composable(NavRoute.Accounts.route)       { AccountsPage(onBack) }
        composable(NavRoute.Tags.route)           { TagsPage(onBack) }
        composable(NavRoute.AddAccount.route)     { AddAccountPage(onBack) }
        composable(NavRoute.AddTransaction.route) { AddTransactionPage(onBack) }
        composable(NavRoute.AddTag.route)         { AddTagPage(onBack) }

        /* Performance related */
        composable(NavRoute.Performance.route)    { PerformancePage() }

        /* Search related */
        composable(NavRoute.Search.route)         { SearchPage() }

        /* Profile related */
        composable(NavRoute.Profile.route)        { 
          ProfilePage(
            onEducateClick = onEducateClick
          )
        }
        composable(NavRoute.Educate.route)        { EducatePage(onBack) }
      }

      /* === Move to user selected start page on startup */
      var movedToStartPage by rememberSaveable { mutableStateOf(false) }
      LaunchedEffect(startRoute) {
        if (!movedToStartPage && startRoute != NavRoute.Home.route) {
          val current = navController.currentBackStackEntry?.destination?.route
          if (current != startRoute) {

            navController.navigate(startRoute) {

              popUpTo(navController.graph.startDestinationId) { 
                inclusive = true
                saveState = true
              }
              launchSingleTop = true
              restoreState    = true
            }
          }
          movedToStartPage = true
        }
      }
    }
  }
}