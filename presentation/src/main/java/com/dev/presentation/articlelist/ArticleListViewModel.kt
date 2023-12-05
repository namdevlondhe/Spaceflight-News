package com.dev.presentation.articlelist

import androidx.lifecycle.viewModelScope
import com.dev.domain.model.Article
import com.dev.domain.usecase.ArticleListUseCase
import com.dev.presentation.base.BaseViewModel
import com.dev.presentation.mapper.NewsArticleResultMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val articleListUseCase: ArticleListUseCase,
    private val articleNewsMapper: NewsArticleResultMapper
): BaseViewModel<ArticleListViewState, ArticleListViewIntent, ArticleListSideEffect>() {

    init {
        fetchArticleList()
    }
    fun fetchArticleList(){
        viewModelScope.launch {
            articleListUseCase().onStart {
                _state.emit(ArticleListViewState.Loading)
            }.catch {
                _state.emit(ArticleListViewState.Error(it))
            }.collect{
                _state.emit(ArticleListViewState.Success(articleNewsMapper.mapFromModel(it)))
            }
        }
    }

    override fun sendIntent(intent: ArticleListViewIntent) {

    }
}