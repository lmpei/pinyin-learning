package com.example.pinyinlearning.ui.progress

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 学习进度页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    viewModel: ProgressViewModel,
    onNavigateBack: () -> Unit
) {
    val progress by viewModel.progress.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("学习进度") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 统计卡片
            StatCard(
                title = "已学习拼音",
                value = "${progress.learnedPinyins.size}",
                modifier = Modifier.fillMaxWidth()
            )
            
            StatCard(
                title = "练习次数",
                value = "${progress.practiceCount}",
                modifier = Modifier.fillMaxWidth()
            )
            
            StatCard(
                title = "正确次数",
                value = "${progress.correctCount}",
                modifier = Modifier.fillMaxWidth()
            )
            
            StatCard(
                title = "正确率",
                value = "${(progress.getAccuracy() * 100).toInt()}%",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * 统计卡片
 */
@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

