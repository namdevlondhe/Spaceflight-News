package com.dev.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dev.presentation.R
import com.dev.presentation.articlelist.ArticleListScreen
import com.dev.presentation.articledetail.ArticleDetailScreen
import com.dev.presentation.constants.Constant.ARTICLE_ID
import com.dev.presentation.ui.common.BaseScreen

/**
 * This composable function is used to add the manage the screen for the module
 * @param navController - Nav host controller to manage the screens for navigation
 */
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.ArticleListScreen.route
    ) {
        composable(AppScreens.ArticleListScreen.route) {
            BaseScreen(
                title = stringResource(id = R.string.title),
                showBackButton = false,
                onBackClicked = {}) {
                ArticleListScreen {
                        navController.navigate(AppScreens.ArticleDetailScreen.route + "/${it}")
                }
            }
        }
        composable(
            "${AppScreens.ArticleDetailScreen.route}/{${ARTICLE_ID}}",
            arguments = listOf(navArgument(ARTICLE_ID) { type = NavType.IntType })
        ) { navBackStackEntry ->
            val articleId = navBackStackEntry.arguments?.getInt(ARTICLE_ID)
            articleId?.let {
                BaseScreen(
                    title = "Character Detail",
                    showBackButton = true,
                    onBackClicked = {
                        navController.popBackStack()
                    }) {
                    ArticleDetailScreen(
                        id = articleId
                    )
                }
            }
        }
    }
}