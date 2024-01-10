package com.dev.presentation.articledetail

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.dev.presentation.articlelist.ArticleListScreenTest.Companion.TITLE
import com.dev.presentation.articlelist.ArticleListScreenTest.Companion.articleItem
import org.junit.Rule
import org.junit.Test

class ArticleDetailScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun test_characters_list_screen() {
        testRule.setContent {
            DetailDataScreen(articleItem)
        }

        testRule.run {
            onNodeWithText(TITLE).assertExists()
        }
    }
}