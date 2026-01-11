package com.example.pinyinlearning.ui.pinyin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
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
import com.example.pinyinlearning.data.model.Pinyin
import com.example.pinyinlearning.data.model.PinyinCategory

/**
 * 拼音展示页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinyinScreen(
    viewModel: PinyinViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("拼音展示") },
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
                .padding(16.dp)
        ) {
            // 分类筛选
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.selectedCategory == null,
                    onClick = { viewModel.filterByCategory(null) },
                    label = { Text("全部") }
                )
                FilterChip(
                    selected = uiState.selectedCategory == PinyinCategory.SHENGMU,
                    onClick = { viewModel.filterByCategory(PinyinCategory.SHENGMU) },
                    label = { Text("声母") }
                )
                FilterChip(
                    selected = uiState.selectedCategory == PinyinCategory.YUNMU,
                    onClick = { viewModel.filterByCategory(PinyinCategory.YUNMU) },
                    label = { Text("韵母") }
                )
                FilterChip(
                    selected = uiState.selectedCategory == PinyinCategory.FULL,
                    onClick = { viewModel.filterByCategory(PinyinCategory.FULL) },
                    label = { Text("完整拼音") }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 搜索框
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.searchPinyins(it) },
                label = { Text("搜索拼音") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 拼音网格
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 80.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.filteredPinyins) { pinyin ->
                    PinyinItem(
                        pinyin = pinyin,
                        onClick = { viewModel.playPinyin(pinyin) }
                    )
                }
            }
        }
    }
}

/**
 * 拼音项组件
 */
@Composable
fun PinyinItem(
    pinyin: Pinyin,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = pinyin.text,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            if (pinyin.tone > 0) {
                Text(
                    text = "${pinyin.tone}声",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "播放",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}

