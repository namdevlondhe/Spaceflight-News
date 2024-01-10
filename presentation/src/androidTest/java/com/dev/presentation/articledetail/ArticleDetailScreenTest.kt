package com.dev.presentation.articledetail

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.dev.presentation.model.NewsArticle
import org.junit.Rule
import org.junit.Test

class ArticleDetailScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun test_characters_list_screen() {
        testRule.setContent {
            DetailDataScreen(getCharacterItem())
        }
        testRule.run {
            onNodeWithText(TITLE).assertExists()
        }
    }

    private fun getCharacterItem(): NewsArticle {
        return NewsArticle(
            id = 1,
            title = "Title",
            url = "https://url.com",
            image_url  = "https://utl.com",
            news_site = "site",
            summary = "Aummary",
            published_at = "10/10/2023",
            featured = false
        )
    }

    private companion object {
        const val TITLE = "Title"
        const val SUMMARY = "Aummary"
    }
}