package com.dev.presentation.navigation

import com.dev.presentation.constants.Constant.ARTICLE_DETAIL_ROUTE
import com.dev.presentation.constants.Constant.ARTICLE_LIST_ROUTE

/**
 * This class is used to store all the screen for the module
 */
sealed class AppScreens(val route: String) {
    object ArticleListScreen : AppScreens(route = ARTICLE_LIST_ROUTE)
    object ArticleDetailScreen : AppScreens(route = ARTICLE_DETAIL_ROUTE)
}