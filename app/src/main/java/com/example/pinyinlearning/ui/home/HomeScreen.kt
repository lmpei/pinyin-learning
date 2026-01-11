package com.example.pinyinlearning.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 主页面
 */
@Composable
fun HomeScreen(
    onNavigateToPinyin: () -> Unit,
    onNavigateToPractice: () -> Unit,
    onNavigateToProgress: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "拼音学习",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 48.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // 拼音展示按钮
        Button(
            onClick = onNavigateToPinyin,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Text(
                text = "拼音展示",
                fontSize = 18.sp
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 练习按钮
        Button(
            onClick = onNavigateToPractice,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Text(
                text = "开始练习",
                fontSize = 18.sp
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 学习进度按钮
        OutlinedButton(
            onClick = onNavigateToProgress,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Text(
                text = "学习进度",
                fontSize = 18.sp
            )
        }
    }
}

