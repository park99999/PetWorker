package com.petpath.walk.screen.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.petpath.walk.R
import com.petpath.walk.chat.SocketManager.navController
import com.petpath.walk.screen.util.CustomButton
import com.petpath.walk.screen.util.TopBarComponent
import com.petpath.walk.theme.PetWorkerTheme

@Composable
fun RegisterAgreement(navController: NavHostController) {
    var checkedAll by remember { mutableStateOf(false) }
    var checked1 by remember { mutableStateOf(false) }
    var checked2 by remember { mutableStateOf(false) }
    var checked3 by remember { mutableStateOf(false) }
    var checked4 by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarComponent(
            R.drawable.ic_backbtn,
            R.drawable.empty,
            R.drawable.empty,
            R.drawable.ic_close,"회원가입"){navController.popBackStack()}
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 76.dp)) {
            Text("원활한 산책 환경 조성을 위해", style = MaterialTheme.typography.titleMedium)
            Text("서비스 이용에 동의해주세요!",style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
        }
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 296.dp)) {
            Row(modifier = Modifier.fillMaxWidth().background(color = if(checkedAll) colorResource(R.color.primary100) else colorResource(R.color.neutral04), shape = RoundedCornerShape(5.dp)), verticalAlignment = Alignment.CenterVertically) {
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
                    modifier = Modifier.padding(start = 12.dp),
                    checked = checkedAll,
                    onCheckedChange = { checkedAll = it }
                )
                Text("약관 전체 동의", style = MaterialTheme.typography.bodySmall)
                Text("전체 약관에 동의합니다", color = colorResource(R.color.gray600), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 4.dp))
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
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
                    checked = checked1||checkedAll,
                    onCheckedChange = { checked1 = it }
                )
                Text("만 19세 이상입니다(필수)", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.weight(1f))
                Text("자세히 보기", color = colorResource(R.color.gray600), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 4.dp))
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
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
                    checked = checked2||checkedAll,
                    onCheckedChange = { checked2 = it }
                )
                Text("서비스 이용 약관(필수)", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.weight(1f))
                Text("자세히 보기", color = colorResource(R.color.gray600), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 4.dp))
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
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
                    checked = checked3||checkedAll,
                    onCheckedChange = { checked3 = it }
                )
                Text("개인정보 처리 방침(필수)", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.weight(1f))
                Text("자세히 보기", color = colorResource(R.color.gray600), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 4.dp))
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
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
                    checked = checked4||checkedAll,
                    onCheckedChange = { checked4 = it }
                )
                Text("마케팅 이용 정보 동의(선택)", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.weight(1f))
                Text("자세히 보기", color = colorResource(R.color.gray600), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 4.dp))
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(checkedAll||(checked1&&checked2&&checked3),"다음") {
            navController.navigate("registerVerify")
        }
    }
}


@Preview
@Composable
fun SimpleComposablePreview2() {
    PetWorkerTheme {
        //RegisterAgreement(navController)
    }
}