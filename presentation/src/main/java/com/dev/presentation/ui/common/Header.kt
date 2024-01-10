package com.dev.presentation.ui.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dev.presentation.R
import com.dev.presentation.ui.theme.SubTextColor
import com.dev.presentation.ui.theme.UiSize
import com.dev.presentation.ui.theme.UiSize.Companion.UI_SP_SIZE_16

@Composable
 fun HeaderTitle(title:String){
    Spacer(modifier = Modifier.height(UiSize.UI_SIZE_20))
    Text(
        text = title,
        style = TextStyle(
            fontSize = UI_SP_SIZE_16,
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            fontWeight = FontWeight(500),
            color = SubTextColor
        )
    )
}