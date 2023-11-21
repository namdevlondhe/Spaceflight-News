package com.dev.presentation.articlelist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dev.domain.model.Article
import com.dev.domain.usecase.GetArticleUseCase
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
    private val articleListUseCase: GetArticleUseCase
): ViewModel(){

    private var curPage = 0

    var articleList = mutableStateOf<List<Article>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)


    fun getArticleList(){
        articleListUseCase.execute(
            onSuccess = {
                isLoading.value = true
                articleList.value = it
            },
            onError = {
                loadError.value = it.message.toString()
                it.printStackTrace()
            }
        )
    }
}