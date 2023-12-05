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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    title: String,
    showBackButton: Boolean,
    onBackClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.background(color = Color(0xFFDEEDED)),
        topBar = {
            TopAppBar(

                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFDEEDED)),

                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 14.dp)
                    ) {
                        if (showBackButton) {
                            IconButton(onClick = { onBackClicked.invoke() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = null)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BaseScreen(
        title = stringResource(id = R.string.title),
        showBackButton = true,
        onBackClicked = { /*TODO*/ }) {
        Text(text = "Hello")
    }
}