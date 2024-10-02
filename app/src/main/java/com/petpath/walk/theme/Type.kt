package com.petpath.walk.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.petpath.walk.R
import retrofit2.http.Body


val Pretendard = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold)
)

// Figma에서 설정한 Heading01 스타일을 Compose로 변환
val Heading00 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 32.sp,           // 28px -> 28sp
    lineHeight = 28.sp,         // Line height 150% of 28px -> 42sp
    letterSpacing = 0.5.sp     // Letter spacing 0.5px -> 0.5em
)
val Heading01 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 28.sp,           // 28px -> 28sp
    lineHeight = 28.sp,         // Line height 150% of 28px -> 42sp
    letterSpacing = 0.5.sp     // Letter spacing 0.5px -> 0.5em
)
val Heading02 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,           // 28px -> 28sp
    lineHeight = 28.sp,         // Line height 150% of 28px -> 42sp
    letterSpacing = 0.5.sp      // Letter spacing 0.5px -> 0.5em
)
val Heading03 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,           // 28px -> 28sp
    lineHeight = 28.sp,         // Line height 150% of 28px -> 42sp
    letterSpacing = 0.5.sp     // Letter spacing 0.5px -> 0.5em
)
val Heading04 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,           // 28px -> 28sp
    lineHeight = 28.sp,         // Line height 150% of 28px -> 42sp
    letterSpacing = 0.5.em      // Letter spacing 0.5px -> 0.5em
)
val Body01 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,           // 28px -> 28sp
    lineHeight = 14.sp,         // Line height 150% of 28px -> 42sp
    letterSpacing = 0.5.sp     // Letter spacing 0.5px -> 0.5em
)
val Body04 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.SemiBold,
    fontSize = 12.sp,           // 28px -> 28sp
    lineHeight = 14.sp,         // Line height 150% of 28px -> 42sp
    letterSpacing = 0.5.sp     // Letter spacing 0.5px -> 0.5em
)
val Caption01 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,           // 28px -> 28sp
    lineHeight = 14.sp,         // Line height 150% of 28px -> 42sp
    letterSpacing = 0.5.sp     // Letter spacing 0.5px -> 0.5em
)
val Caption02 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.SemiBold,
    fontSize = 10.sp,           // 28px -> 28sp
    lineHeight = 14.sp,         // Line height 150% of 28px -> 42sp
    letterSpacing = 0.5.sp     // Letter spacing 0.5px -> 0.5em
)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = Heading00,
    titleMedium = Heading02,
    titleSmall = Heading03,
    bodyMedium = Body01,
    bodySmall = Body04,
    labelLarge = Caption01,
    labelSmall = Caption02


)