package com.dev.presentation.artistdetail

import androidx.lifecycle.viewModelScope
import com.dev.domain.usecase.GetArticleDetailUseCase
import com.dev.presentation.articlelist.ArticleListViewIntent
import com.dev.presentation.base.BaseViewModel
import com.dev.presentation.mapper.NewsArticleMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val getArticleDetailsUseCase: GetArticleDetailUseCase,
    private val articleMapper: NewsArticleMapper
) : BaseViewModel<ArticleDetailViewState, ArticleDetailViewIntent, ArticleDetailSideEffect>() {

     private fun fetchArticleInfo(id: Int) {
        viewModelScope.launch {
            getArticleDetailsUseCase(id).onStart {
                _state.emit(ArticleDetailViewState.Loading)
            }.catch {
                _state.emit(ArticleDetailViewState.Error(it))
            }.collect{
                _state.emit(ArticleDetailViewState.Success(articleMapper.mapFromModel(it)))
            }
        }
    }

    override fun sendIntent(intent: ArticleDetailViewIntent) {
        when(intent){
            is ArticleDetailViewIntent.LoadData -> {
                fetchArticleInfo(intent.id)
            }
        }
    }
}
