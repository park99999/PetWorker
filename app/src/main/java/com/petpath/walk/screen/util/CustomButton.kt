package com.petpath.walk.screen.util

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.petpath.walk.R

@Composable
fun CustomButton(enabled :Boolean,text : String,onClick: () -> Unit) {
    Column (modifier = Modifier.fillMaxWidth().padding(16.dp).imePadding(), horizontalAlignment = Alignment.CenterHorizontally){
        FilledTonalButton(onClick = { onClick() },
            colors = ButtonColors(containerColor = colorResource(R.color.primary500), colorResource(
                R.color.neutral11),
                colorResource(R.color.neutral03), colorResource(R.color.neutral06)
            ),
            enabled = enabled,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.width(328.dp).height(40.dp)
        ) {
            Text(text=text)
        }
    }
}