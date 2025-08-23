/* Author: Green Apple
 * Date Created: August 5, 2025
 * 
 * Note:
 */

package net.greenapple.arrestwt.ui

// ====== IMPORTS ======
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*

// ====== NAVIGATION ROUTES ======
sealed class NavRoute(
  val route:  String,
  val label:  String,
  val icon:   ImageVector
) {

  /* ===== Routes */
  /* === Home Route */
  object Home: NavRoute(
    route = "home",
    label = "Home",
    icon  = Icons.Outlined.Wallet
  )

  /* === Notifications Route */
  object Notifications: NavRoute(
    route = "notifications",
    label = "Notifications",
    icon  = Icons.Outlined.Notifications
  )

  /* === Settings Route */
  object Settings: NavRoute(
    route = "settings",
    label = "Settings",
    icon  = Icons.Outlined.Settings
  )

  /* === Transactions Route */
  object Transactions: NavRoute(
    route = "transactions",
    label = "Transactions",
    icon  = Icons.Outlined.ShoppingBag
  )

  /* === Accounts Route */
  object Accounts: NavRoute(
    route = "accounts",
    label = "Accounts & Cards",
    icon  = Icons.Outlined.AccountBalance
  )

  /* === Tags Route */
  object Tags: NavRoute(
    route = "tags",
    label = "Tags",
    icon  = Icons.Outlined.Label
  )

  /* === Add Transaction Route */
  object AddTransaction: NavRoute(
    route = "add-transaction",
    label = "Add Transaction",
    icon  = Icons.Outlined.PriceChange
  )

  /* === Add Account Route */
  object AddAccount: NavRoute(
    route = "add-account",
    label = "Add Account",
    icon  = Icons.Outlined.AddHome
  )

  /* === Add Card Route */
  object AddCard: NavRoute(
    route = "add-card",
    label = "Add Card",
    icon  = Icons.Outlined.AddCard
  )

  /* === Add Tag Route */
  object AddTag: NavRoute(
    route = "add-tag",
    label = "Add Tag",
    icon  = Icons.Outlined.NewLabel
  )

  /* === Performance Route */
  object Performance: NavRoute(
    route = "performance",
    label = "Performance",
    icon  = Icons.Outlined.AreaChart
  )

  /* === Search Route */
  object Search: NavRoute(
    route = "search",
    label = "Search",
    icon  = Icons.Outlined.Search
  )

  /* === Stock Route */
  object Stock: NavRoute(
    route = "stock",
    label = "Stock",
    icon  = Icons.Outlined.StarBorder
  )

  /* === Profile Route */
  object Profile: NavRoute(
    route = "profile",
    label = "Profile",
    icon  = Icons.Outlined.Person
  )

  /* === Educate Route */
  object Educate: NavRoute(
    route = "educate",
    label = "Educate",
    icon  = Icons.Outlined.School
  )

  /* ===== Bottom Bar Display Routes */
  companion object {
    val bottomNavRoutes = listOf(Home, Transactions, Performance, Search, Profile)
  }
}