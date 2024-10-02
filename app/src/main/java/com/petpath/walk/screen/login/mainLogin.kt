package com.petpath.walk.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.petpath.walk.R
import com.petpath.walk.screen.util.CustomButton
import com.petpath.walk.theme.PetWorkerTheme
import com.petpath.walk.theme.Pretendard
import com.petpath.walk.viewModel.KeyboardVisibilityViewModel

@Composable
fun LoginScreen(
    keyboardVisibilityViewModel: KeyboardVisibilityViewModel,
    navController: NavHostController
) {
    val isKeyboardVisible by keyboardVisibilityViewModel.isKeyboardVisible.observeAsState(false)
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isIdFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(top = if(isKeyboardVisible) 48.dp else 120.dp, bottom = 66.dp)) {
            Image(painter = painterResource(R.drawable.petpath_logo), contentDescription = "main_logo"
            , modifier = Modifier
                    .width(120.dp)
                    .height(92.dp))
            Text(text = "펫패스", style = MaterialTheme.typography.titleLarge,
                color = colorResource(R.color.neutral10),
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp))
            Text(text = "사람과 강아지가 함께 걷는 길"
            ,color = colorResource(R.color.neutral07),
                style = MaterialTheme.typography.titleSmall)
        }
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            Text("아이디", color = if(isIdFocused) colorResource(R.color.neutral10) else colorResource(R.color.neutral06), style = MaterialTheme.typography.bodySmall)
            BasicTextField(
                value = userId,
                onValueChange = { userId = it },
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.Transparent,shape = RoundedCornerShape(8.dp))
                            .border(color = if(isIdFocused) colorResource(R.color.neutral10) else colorResource(R.color.neutral06), width = 1.dp, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(width = 12.dp))
                        if(userId.isEmpty()){
                            Text("아이디", color = colorResource(R.color.neutral06), style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.width(width = 8.dp))
                        }
                        innerTextField()
                    }
                },
                textStyle = TextStyle(
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,           // 28px -> 28sp
                    lineHeight = 14.sp,         // Line height 150% of 28px -> 42sp
                    letterSpacing = 0.5.sp     // Letter spacing 0.5px -> 0.5em)
                ),
                modifier = Modifier.onFocusChanged { focusState ->
                    isIdFocused = focusState.isFocused // 포커스 상태가 변경될 때 호출
                }
            )
            Text("비밀번호",color = if(isPasswordFocused) colorResource(R.color.neutral10) else colorResource(R.color.neutral06), style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 20.dp))
            Spacer(modifier = Modifier.height(6.dp))
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.Transparent,shape = RoundedCornerShape(8.dp))
                            .border(color = if(isPasswordFocused) colorResource(R.color.neutral10) else colorResource(R.color.neutral06), width = 1.dp, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(width = 12.dp))
                        if(password.isEmpty()) {
                            Text(
                                "비밀번호",
                                color = colorResource(R.color.neutral06),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.width(width = 8.dp))
                        }
                        innerTextField()
                    }
                },
                textStyle = TextStyle(
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,           // 28px -> 28sp
                    lineHeight = 14.sp,         // Line height 150% of 28px -> 42sp
                    letterSpacing = 0.5.sp     // Letter spacing 0.5px -> 0.5em)
                ),
                modifier = Modifier.onFocusChanged { focusState ->
                    isPasswordFocused = focusState.isFocused // 포커스 상태가 변경될 때 호출
                }
            )
            Row(modifier = Modifier.fillMaxWidth().padding(top = 24.dp), horizontalArrangement = Arrangement.Center) {
                Text("아이디 찾기", style = MaterialTheme.typography.labelLarge, color = colorResource(R.color.gray600),modifier = Modifier.clickable { navController.navigate("findId") })
                Text("  |  ",style = MaterialTheme.typography.labelLarge, color = colorResource(R.color.gray600))
                Text("비밀번호 찾기",style = MaterialTheme.typography.labelLarge, color = colorResource(R.color.gray600),modifier = Modifier.clickable { navController.navigate("findPw") })
                Text("  |  ",style = MaterialTheme.typography.labelLarge, color = colorResource(R.color.gray600))
                Text("회원가입 ",style = MaterialTheme.typography.labelLarge, color = colorResource(R.color.gray600), modifier = Modifier.clickable{navController.navigate("registerAgreement")})

            }
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(enabled = !(userId.isEmpty()||password.isEmpty()),"로그인하기") { loginBtnClickListner() }
    }
}

@Composable
fun LoginInputField(userId : String){

}
fun loginBtnClickListner(){}

@Preview
@Composable
fun SimpleComposablePreview() {
    PetWorkerTheme {
        //LoginScreen(keyboardVisibilityViewModel)
    }
}
