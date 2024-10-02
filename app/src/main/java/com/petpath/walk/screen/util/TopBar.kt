package com.petpath.walk.screen.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.petpath.walk.R


@Composable
fun TopBarComponent(firstIcon : Int, secondIcon : Int, thirdIcon : Int, fourthIcon : Int,text : String, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(firstIcon), contentDescription = "back_btn",
            tint = colorResource(R.color.component500),
            modifier = Modifier.padding(start = 16.dp).padding(vertical = 10.dp).clickable { onClick() })
        Text(text,
            modifier = Modifier.padding(vertical = 12.5.dp),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_bold))
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.padding(vertical = 10.dp).padding(end = 8.dp)) {
            Icon(painter = painterResource(secondIcon), contentDescription = "back_btn",
                tint = colorResource(R.color.component500),
                modifier = Modifier.padding(start = 8.dp))
            Icon(painter = painterResource(thirdIcon), contentDescription = "back_btn",
                tint = colorResource(R.color.component500),
                modifier = Modifier.padding(start = 8.dp))
            Icon(painter = painterResource(fourthIcon), contentDescription = "back_btn",
                tint = colorResource(R.color.component500),
                modifier = Modifier.padding(start = 8.dp).clickable { onClick() })
        }
    }
}
@Preview
@Composable
fun Testscreen(modifier: Modifier = Modifier) {
    //TopBarComponent(R.drawable.ic_backbtn,R.drawable.empty,R.drawable.empty,R.drawable.ic_close,"회원가입")
}