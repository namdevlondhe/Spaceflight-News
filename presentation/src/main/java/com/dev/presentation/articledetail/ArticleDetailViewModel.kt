package com.dev.presentation.articledetail

import androidx.lifecycle.viewModelScope
import com.dev.domain.usecase.GetArticleDetailUseCase
import com.dev.presentation.base.BaseViewModel
import com.dev.presentation.mapper.NewsArticleMapper
import dagger.hilt.android.lifecycle.HiltViewModel
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
            getArticleDetailsUseCase(id).onSuccess {
                state.emit(
                    ArticleDetailViewState.Success(
                        articleMapper.map(
                            it
                        )
                    )
                )
            }.onFailure {
                it.message?.let { it1 -> ArticleDetailViewState.Error(it1) }
                    ?.let { it2 -> state.emit(it2) }
            }
        }
    }

    /**
     * This function is responsible for taking the actions from the UI
     * @param intent - intent is the action
     */
    override fun sendIntent(intent: ArticleDetailViewIntent) {
        when (intent) {
            is ArticleDetailViewIntent.LoadData -> fetchArticleInfo(intent.id)
        }
    }

    override fun createInitialState() = ArticleDetailViewState.Loading

}
