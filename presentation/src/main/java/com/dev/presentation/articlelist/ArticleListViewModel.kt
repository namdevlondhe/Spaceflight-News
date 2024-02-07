package com.dev.presentation.articlelist

import androidx.lifecycle.viewModelScope
import com.dev.domain.usecase.GetArticleListUseCase
import com.dev.presentation.base.BaseViewModel
import com.dev.presentation.mapper.NewsArticleResultMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This class is responsible for providing the data for news article list
 */
@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val articleListUseCase: GetArticleListUseCase,
    private val articleNewsMapper: NewsArticleResultMapper
) : BaseViewModel<ArticleListViewState, ArticleListViewIntent, ArticleListSideEffect>() {

    /**
     * This function responsible for fetching the list of news articles
     */
    fun fetchArticleList() {
        viewModelScope.launch {
            val result = articleListUseCase.invoke()
            when {
                result.isSuccess ->
                    try {
                        state.emit(
                            ArticleListViewState.Success(
                                articleNewsMapper.map(
                                    result.getOrNull()!!
                                )
                            )
                        )
                    } catch (e: Exception) {
                        state.emit(ArticleListViewState.Error(result.exceptionOrNull()!!))
                    }

                result.isFailure -> state.emit(ArticleListViewState.Error(result.exceptionOrNull()!!))
            }
        }
    }

    /**
     * This function is used for navigation to details screen
     * @param id - id for the news article
     */
    private fun navigateToDetails(id: Int) {
        viewModelScope.launch {
            sideEffect.emit(ArticleListSideEffect.NavigateToDetails(id))
        }
    }

    /**
     * This function is responsible for taking the actions from the UI
     * @param intent - intent is the action
     */
    override fun sendIntent(intent: ArticleListViewIntent) {
        when (intent) {
            is ArticleListViewIntent.LoadData -> fetchArticleList()
            is ArticleListViewIntent.OnArticleClick -> navigateToDetails(intent.id)
        }
    }

    override fun createInitialState() = ArticleListViewState.Loading

}