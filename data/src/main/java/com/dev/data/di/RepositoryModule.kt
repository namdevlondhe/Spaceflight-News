package com.dev.data.di

import com.dev.data.mapper.ArticleResultDataMapper
import com.dev.data.repository.ArticleRepositoryImpl
import com.dev.data.source.remote.RetrofitService
import com.dev.domain.repository.ArticleRepository
import com.dev.domain.usecase.GetArticleListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideArticleRepository(
        retrofitService: RetrofitService,
        articleResultDataMapper: ArticleResultDataMapper
    ): ArticleRepository =
        ArticleRepositoryImpl(retrofitService, articleResultDataMapper)

    @Singleton
    @Provides
    fun providesArticleListUseCase(
        articleRepository: ArticleRepository
    ) = GetArticleListUseCase(articleRepository)
}