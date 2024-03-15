package com.dev.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dev.presentation.R
import com.dev.presentation.ui.theme.BackgroundColor
import com.dev.presentation.ui.theme.UiSize.Companion.SIZE_1
import com.dev.presentation.ui.theme.UiSize.Companion.SIZE_14

/**
 * This composable function is used for base screen for tool bar and action handling for back
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    title: String,
    showBackButton: Boolean,
    onBackClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.background(color = BackgroundColor),
        topBar = {
            TopAppBar(

                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = BackgroundColor),

                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = SIZE_1)
                    ) {
                        if (showBackButton) {
                            IconButton(onClick = { onBackClicked.invoke() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.content_description_back_button))
                            }
                        }
                        Text(
                            text = title
                        )
                    }
                },
            )
        },
    ) {
        Surface(modifier = Modifier.padding(it)) {
            content()
        }
    }
}