package com.dev.presentation.articlelist

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.dev.presentation.model.NewsArticle
import org.junit.Rule
import org.junit.Test

class ArticleListScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun test_characters_list_screen() {
        testRule.setContent {
            ArticleEntry(articleItem)
        }
        testRule.run {
            onNodeWithText(TITLE).assertExists()
        }
    }

    companion object {
        const val TITLE = "Title"
        const val SUMMARY = "Aummary"
        val articleItem = NewsArticle(
            id = 1,
            title = "Title",
            url = "https://url.com",
            imageUrl  = "https://utl.com",
            newsSite = "site",
            summary = "Aummary",
            publishedAt = "10/10/2023",
            featured = false
        )
    }
}