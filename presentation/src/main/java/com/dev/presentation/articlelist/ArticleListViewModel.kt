package com.dev.presentation.articlelist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dev.domain.model.Article
import com.dev.domain.usecase.GetArticleListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val articleListUseCase: GetArticleListUseCase
): ViewModel(){

    private var curPage = 0

    var articleList = mutableStateOf<List<Article>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)


    fun getArticleList(){
        /*articleListUseCase.invoke(
            onSuccess = {
                isLoading.value = true
                articleList.value = it
            },
            onError = {
                loadError.value = it.message.toString()
                it.printStackTrace()
            }
        )*/
    }
}