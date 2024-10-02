package com.petpath.walk.screen.register

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.petpath.walk.R
import com.petpath.walk.screen.util.CustomButton
import com.petpath.walk.screen.util.TopBarComponent
import com.petpath.walk.theme.Pretendard
import com.petpath.walk.viewModel.UserNavigationEvent
import com.petpath.walk.viewModel.UserViewModel

@Composable
fun EmailSettingScreen(navController: NavController, userViewModel: UserViewModel) {
    var email by remember { mutableStateOf("") }
    var isEmailFocused by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(false) }

    // 이메일 정규식 (간단한 형식)
    val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarComponent(
            R.drawable.ic_backbtn,
            R.drawable.empty,
            R.drawable.empty,
            R.drawable.ic_close,"회원가입"){
            navController.popBackStack()
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 76.dp)) {
            Text("이메일을", style = MaterialTheme.typography.titleMedium)
            Text("입력해주세요",style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)) {
            Text(
                "이메일 설정",
                color = if (isEmailFocused) colorResource(R.color.neutral10) else colorResource(R.color.neutral06),
                style = MaterialTheme.typography.bodySmall
            )
            BasicTextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid = emailPattern.matches(email)  // 이메일 형식 검사
                },
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                            .border(
                                color = if (isEmailFocused) colorResource(R.color.neutral10) else colorResource(
                                    R.color.neutral06
                                ), width = 1.dp, shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(width = 12.dp))
                        if (email.isEmpty()) {
                            Text(
                                "이메일",
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
                    isEmailFocused = focusState.isFocused // 포커스 상태가 변경될 때 호출
                }
            )
            if (email.isNotEmpty()) {
                Text(
                    text = if (isEmailValid) "올바른 이메일 형식입니다." else "이메일 형식이 잘못되었습니다.",
                    color = if (isEmailValid) Color.Green else Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        CustomButton(isEmailValid,"다음") {
            userViewModel.setEmail(email)
            navController.navigate(route = "passwordSetting")
        }

    }
}
@Composable
fun PasswordSettingScreen(navController: NavController,userViewModel: UserViewModel) {
    var password by remember { mutableStateOf("") }
    var re_password by remember { mutableStateOf("") }
    var isPasswordFocused by remember { mutableStateOf(false) }
    var isRePasswordFocused by remember { mutableStateOf(false) }
    var isRePasswordValid by remember { mutableStateOf(false) }
    val navigationEvent by userViewModel.navigationEvent.collectAsState()
    // 비밀번호 정규식
    val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*[\\d!@#$%^&*?])[A-Za-z\\d!@#$%^&*?]{8,}\$")

    // 비밀번호가 유효한지 확인
    val isValidPassword = passwordRegex.matches(password)

    // 비밀번호 길이 확인
    val isLongEnough = password.length >= 8
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarComponent(
            R.drawable.ic_backbtn,
            R.drawable.empty,
            R.drawable.empty,
            R.drawable.ic_close,"회원가입"){
            navController.popBackStack()
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 76.dp)) {
            Text("비밀번호를", style = MaterialTheme.typography.titleMedium)
            Text("설정해주세요",style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)) {
            Text(
                "비밀번호 설정",
                color = if (isPasswordFocused) colorResource(R.color.neutral10) else colorResource(R.color.neutral06),
                style = MaterialTheme.typography.bodySmall
            )
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
                            .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                            .border(
                                color = if (isPasswordFocused) colorResource(R.color.neutral10) else colorResource(
                                    R.color.neutral06
                                ), width = 1.dp, shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(width = 12.dp))
                        if (password.isEmpty()) {
                            Text(
                                "비밀번호 설정",
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
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp), verticalArrangement = Arrangement.Center) {
                Row {
                    Icon(
                        painter = painterResource(R.drawable.ic_circle),
                        contentDescription = null,
                        tint = if(isValidPassword) colorResource(R.color.secondary600) else colorResource(R.color.secondary300), // 사용자가 원하는 색상으로 변경
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "영문 대/소문자, 숫자, 특수문자 중 2개 포함",
                            style = MaterialTheme.typography.labelLarge,
                            color = colorResource(R.color.gray500)
                        )
                        Text(
                            text = "(특수문자는 !@#$%^&*?만 가능)",
                            style = MaterialTheme.typography.labelSmall,
                            color = colorResource(R.color.gray500),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_circle),
                        contentDescription = null,
                        tint = if(isLongEnough) colorResource(R.color.secondary600) else colorResource(R.color.secondary300),
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "8자리 이상",
                        style = MaterialTheme.typography.labelLarge,
                        color = colorResource(R.color.gray500)
                    )
                }
            }
            Text(
                "비밀번호 확인",
                color = if(!isRePasswordFocused) colorResource(R.color.neutral06) else if(isRePasswordValid) colorResource(R.color.texttrue) else colorResource(R.color.textfalse),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 16.dp)
            )
            BasicTextField(
                value = re_password,
                onValueChange = {
                    re_password = it
                    isRePasswordValid = (password == re_password)
                                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                            .border(
                                color = if (!isRePasswordFocused) colorResource(R.color.neutral06) else if (isRePasswordValid) colorResource(
                                    R.color.texttrue
                                ) else colorResource(R.color.textfalse),
                                width = 1.dp, shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(width = 12.dp))
                        if (re_password.isEmpty()) {
                            Text(
                                "비밀번호 확인",
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
                    isRePasswordFocused = focusState.isFocused // 포커스 상태가 변경될 때 호출
                }
            )
            if(re_password.isNotEmpty()){
                Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(top = 8.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.ic_circle),
                        contentDescription = null,
                        tint = if(isRePasswordValid) colorResource(R.color.texttrue) else colorResource(R.color.texterror),
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isRePasswordValid) "비밀번호가 일치합니다." else "비밀번호가 일치하지 않습니다.",
                        style = MaterialTheme.typography.labelLarge,
                        color = colorResource(R.color.gray500)
                    )
                }
            }

        }
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(isValidPassword&&isLongEnough&&isRePasswordValid,"회원가입 완료") {
            userViewModel.setPassword(re_password)
            userViewModel.signUp()
        }

    }
    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            is UserNavigationEvent.EmailSetting -> {
                navController.navigate("emailSetting")
                userViewModel.setNavigationEvent()
            }
            is UserNavigationEvent.GoToVerifyFailed -> {
                navController.navigate("verifyFailed")
                userViewModel.setNavigationEvent()
            }
            null -> {
                // 아무 이벤트가 없는 경우
            }

            UserNavigationEvent.SignUPCompleted -> {
                navController.navigate("signupCompleted")
                userViewModel.setNavigationEvent()
            }
        }
    }
}

@Composable
fun SignUpCompletedScreen(navController: NavController,userViewModel: UserViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarComponent(
            R.drawable.ic_backbtn,
            R.drawable.empty,
            R.drawable.empty,
            R.drawable.ic_close,"회원가입"){
            navController.popBackStack()
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 76.dp)) {
            Text("환영합니다", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(true,"펫패스 시작하기") {
            //token 을 통해 자동 로그인하여 홈화면 진입
            navController.navigate("verifyAPI")
        }
    }
}