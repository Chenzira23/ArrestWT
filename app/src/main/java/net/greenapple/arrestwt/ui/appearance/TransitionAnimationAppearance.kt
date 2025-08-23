/* Author: Green Apple
 * Date Created: August 10, 2025
 * 
 * Other Dates:
 * > Refactored: August 16, 2025
 * 
 * Note:
 */

package net.greenapple.arrestwt.ui.appearance

// ====== IMPORTS ======
import net.greenapple.arrestwt.ui.NavRoute
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.toRoute

// ====== TRANSITION ANIMATION STYLE ======
object TransitionAnimationAppearance {

  /* ====== Check if Route is in a List of NavRoutes */
  private fun NavBackStackEntry.isIn(
    routes: List<NavRoute>
  ): Boolean {

    return destination.hierarchy.any { 
      it.route?.let { 
        it in routes.asSequence().map { it.route }.toSet()
      } == true
    }
  }

  /* ====== Page Entering Transition */
  fun enter(
    scope:        AnimatedContentTransitionScope<NavBackStackEntry>,
    bottomRoutes: List<NavRoute>
  ): EnterTransition {

    /* --- Check if transitioning to and from a bottom page */
    val fromBottomRoute = scope.initialState.isIn(bottomRoutes)
    val toBottomRoute   = scope.targetState.isIn(bottomRoutes)

    return when {

      /* --- When going from bottom to bottom */
      fromBottomRoute && toBottomRoute -> {
        val fromIndex = bottomRoutes.indexOfFirst { it.route == scope.initialState.destination.route }
        val toIndex   = bottomRoutes.indexOfFirst { it.route == scope.targetState.destination.route }

        scope.slideIntoContainer(
          towards       = if (fromIndex > toIndex) SlideDirection.Right else SlideDirection.Left,
          animationSpec = tween(200)
        )
      }
      

      /* --- When going from bottom to non-bottom*/
      fromBottomRoute && !toBottomRoute -> scope.slideIntoContainer(
        towards       = SlideDirection.Down,
        animationSpec = tween(200)
      )

      /* --- When going from non-bottom to bottom*/
      !fromBottomRoute && toBottomRoute -> scope.slideIntoContainer(
        towards       = SlideDirection.Up,
        animationSpec = tween(200)
      )

      /* --- Everything else, have no animation */
      else -> EnterTransition.None
    }
  }

  /* ====== Page Exiting Transition */
  fun exit(
    scope:        AnimatedContentTransitionScope<NavBackStackEntry>,
    bottomRoutes: List<NavRoute>
  ): ExitTransition {

    /* --- Check if transitioning to and from a bottom page */
    val fromBottomRoute = scope.initialState.isIn(bottomRoutes)
    val toBottomRoute   = scope.targetState.isIn(bottomRoutes)

    return when {
      /* --- When going from bottom to bottom */
      fromBottomRoute && toBottomRoute -> {
        val fromIndex = bottomRoutes.indexOfFirst { it.route == scope.initialState.destination.route }
        val toIndex   = bottomRoutes.indexOfFirst { it.route == scope.targetState.destination.route }

        scope.slideOutOfContainer(
          towards       = if (fromIndex > toIndex) SlideDirection.Right else SlideDirection.Left,
          animationSpec = tween(200)
        )
      }

      /* --- When going from bottom to non-bottom*/
      fromBottomRoute && !toBottomRoute -> scope.slideOutOfContainer(
        towards       = SlideDirection.Down,
        animationSpec = tween(200)
      )

      /* --- When going from non-bottom to bottom*/
      !fromBottomRoute && toBottomRoute -> scope.slideOutOfContainer(
        towards       = SlideDirection.Up,
        animationSpec = tween(200)
      )

      /* --- Everything else, have no animation */
      else -> ExitTransition.None
    }
  }
}