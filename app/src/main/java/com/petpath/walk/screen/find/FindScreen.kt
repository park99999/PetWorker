package com.petpath.walk.screen.find

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.petpath.walk.R
import com.petpath.walk.screen.util.CustomButton
import com.petpath.walk.screen.util.TopBarComponent

@Composable
fun FindIdScreen(navController: NavController) {
    var checked3 by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarComponent(
            R.drawable.ic_backbtn,
            R.drawable.empty,
            R.drawable.empty,
            R.drawable.ic_close,"아이디 찾기"){
            navController.popBackStack()
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 76.dp)) {
            Text("본인인증서를 통해서", style = MaterialTheme.typography.titleMedium)
            Text("해당 명의로 가입된 계정을",style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
            Text("찾아볼게요",style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp).padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                colors = CheckboxColors(
                    checkedCheckmarkColor = Color.White,
                    checkedBoxColor = colorResource(R.color.primary),
                    uncheckedBoxColor = colorResource(R.color.transparent),
                    uncheckedBorderColor = colorResource(R.color.gray04),
                    uncheckedCheckmarkColor = colorResource(R.color.transparent),
                    checkedBorderColor = colorResource(R.color.primary),
                    disabledBorderColor = Color.White,
                    disabledIndeterminateBorderColor = Color.White,
                    disabledCheckedBoxColor = Color.White,
                    disabledUncheckedBoxColor = Color.White,
                    disabledUncheckedBorderColor = Color.White,
                    disabledIndeterminateBoxColor = Color.White
                ),
                modifier = Modifier,
                checked = checked3,
                onCheckedChange = { checked3 = it }
            )
            Text("개인정보 처리 방침(필수)", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.weight(1f))
            Text("자세히 보기", color = colorResource(R.color.gray600), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 4.dp))
        }
        CustomButton(checked3,"본인인증 하러가기") {
            navController.navigate("verifyAPI")
        }
    }
}
@Composable
fun FindPwScreen(navController: NavController) {
    var checked3 by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarComponent(
            R.drawable.ic_backbtn,
            R.drawable.empty,
            R.drawable.empty,
            R.drawable.ic_close,"비밀번호 찾기"){
            navController.popBackStack()
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 76.dp)) {
            Text("본인인증서를 통해서", style = MaterialTheme.typography.titleMedium)
            Text("해당 명의로 가입된 계정을",style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
            Text("찾아볼게요",style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp).padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                colors = CheckboxColors(
                    checkedCheckmarkColor = Color.White,
                    checkedBoxColor = colorResource(R.color.primary),
                    uncheckedBoxColor = colorResource(R.color.transparent),
                    uncheckedBorderColor = colorResource(R.color.gray04),
                    uncheckedCheckmarkColor = colorResource(R.color.transparent),
                    checkedBorderColor = colorResource(R.color.primary),
                    disabledBorderColor = Color.White,
                    disabledIndeterminateBorderColor = Color.White,
                    disabledCheckedBoxColor = Color.White,
                    disabledUncheckedBoxColor = Color.White,
                    disabledUncheckedBorderColor = Color.White,
                    disabledIndeterminateBoxColor = Color.White
                ),
                modifier = Modifier,
                checked = checked3,
                onCheckedChange = { checked3 = it }
            )
            Text("개인정보 처리 방침(필수)", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.weight(1f))
            Text("자세히 보기", color = colorResource(R.color.gray600), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 4.dp))
        }
        CustomButton(checked3,"본인인증 하러가기") {
            navController.navigate("verifyAPI")
        }
    }
}