package com.dev.presentation.articledetail

import androidx.lifecycle.viewModelScope
import com.dev.domain.usecase.GetArticleDetailUseCase
import com.dev.presentation.base.BaseViewModel
import com.dev.presentation.mapper.NewsArticleMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This class is responsible for providing the data for single news article
 */
@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val getArticleDetailsUseCase: GetArticleDetailUseCase,
    private val articleMapper: NewsArticleMapper
) : BaseViewModel<ArticleDetailViewState, ArticleDetailViewIntent, ArticleDetailSideEffect>() {

    /**
     * This function responsible for fetching the single news article
     */
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

    /**
     * This function is responsible for taking the actions from the UI
     * @param intent - intent is the action
     */
    override fun sendIntent(intent: ArticleDetailViewIntent) {
        when(intent){
            is ArticleDetailViewIntent.LoadData -> {
                fetchArticleInfo(intent.id)
            }
        }
    }
}
