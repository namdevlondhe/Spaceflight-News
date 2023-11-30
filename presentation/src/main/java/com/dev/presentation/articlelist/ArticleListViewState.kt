package com.dev.presentation.articlelist

import com.dev.presentation.base.SideEffect
import com.dev.presentation.base.ViewIntent
import com.dev.presentation.base.ViewState
import com.dev.presentation.model.NewsArticle

sealed interface ArticleListViewState: ViewState {
    object Loading: ArticleListViewState

    class Success(val data: List<NewsArticle>) : ArticleListViewState

    class Error(val throwable: Throwable): ArticleListViewState
}

sealed interface ArticleListViewIntent: ViewIntent {
    object LoadData: ArticleListViewIntent

    class OnCharacterClick(val id: Int): ArticleListViewIntent
}

sealed interface ArticleListSideEffect: SideEffect {
    class NavigateToDetails(val id: Int) : ArticleListSideEffect
}