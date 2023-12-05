package com.dev.presentation.artistdetail

import com.dev.presentation.base.SideEffect
import com.dev.presentation.base.ViewIntent
import com.dev.presentation.base.ViewState
import com.dev.presentation.model.NewsArticle

sealed interface ArticleDetailViewState: ViewState {
    object Loading: ArticleDetailViewState

    class Success(val data: NewsArticle) : ArticleDetailViewState

    class Error(val throwable: Throwable): ArticleDetailViewState
}

sealed interface ArticleDetailViewIntent: ViewIntent {
    class LoadData(val id: Int): ArticleDetailViewIntent
}

sealed interface ArticleDetailSideEffect: SideEffect {
}