package com.example.pinyinlearning.ui.practice

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 练习页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeScreen(
    viewModel: PracticeViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("拼音练习") },
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
                .padding(24.dp)
        ) {
            if (uiState.questions.isEmpty()) {
                // 开始界面
                StartPracticeView(
                    onStartClick = { viewModel.startPractice() }
                )
            } else if (uiState.isFinished) {
                // 完成界面
                PracticeResultView(
                    score = uiState.score,
                    totalQuestions = uiState.totalQuestions,
                    onRestartClick = { viewModel.restart() },
                    onBackClick = onNavigateBack
                )
            } else {
                // 练习界面
                val currentQuestion = uiState.currentQuestion
                if (currentQuestion != null) {
                    QuestionView(
                        question = currentQuestion,
                        selectedAnswer = uiState.selectedAnswer,
                        showResult = uiState.showResult,
                        isCorrect = uiState.isCorrect,
                        progress = uiState.progress,
                        currentIndex = uiState.currentQuestionIndex + 1,
                        totalQuestions = uiState.totalQuestions,
                        onAnswerSelected = { viewModel.selectAnswer(it) },
                        onNextClick = { viewModel.nextQuestion() },
                        onPlayAudioClick = { viewModel.playCurrentQuestionAudio() }
                    )
                }
            }
        }
    }
}

/**
 * 开始练习视图
 */
@Composable
fun StartPracticeView(
    onStartClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "准备开始练习",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        Button(
            onClick = onStartClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Text(
                text = "开始练习",
                fontSize = 18.sp
            )
        }
    }
}

/**
 * 题目视图
 */
@Composable
fun QuestionView(
    question: com.example.pinyinlearning.data.model.Question,
    selectedAnswer: String?,
    showResult: Boolean,
    isCorrect: Boolean,
    progress: Float,
    currentIndex: Int,
    totalQuestions: Int,
    onAnswerSelected: (String) -> Unit,
    onNextClick: () -> Unit,
    onPlayAudioClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 进度条
        Text(
            text = "题目 $currentIndex / $totalQuestions",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 题目
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = question.question,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                if (question.type == com.example.pinyinlearning.data.model.QuestionType.CHOICE) {
                    IconButton(onClick = onPlayAudioClick) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "播放音频",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 选项
        question.options.forEach { option ->
            val isSelected = selectedAnswer == option
            val backgroundColor = when {
                showResult && isSelected && isCorrect -> Color(0xFF4CAF50)
                showResult && isSelected && !isCorrect -> Color(0xFFF44336)
                showResult && option == question.correctAnswer -> Color(0xFF4CAF50)
                isSelected -> MaterialTheme.colorScheme.primaryContainer
                else -> MaterialTheme.colorScheme.surface
            }
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .then(
                        if (!showResult) {
                            Modifier.clickable { onAnswerSelected(option) }
                        } else {
                            Modifier
                        }
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = backgroundColor
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        fontSize = 18.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // 结果显示和下一题按钮
        if (showResult) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isCorrect) "✓ 回答正确！" else "✗ 回答错误",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336),
                    textAlign = TextAlign.Center
                )
            }
            
            Button(
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = if (currentIndex < totalQuestions) "下一题" else "查看结果",
                    fontSize = 18.sp
                )
            }
        }
    }
}

/**
 * 练习结果视图
 */
@Composable
fun PracticeResultView(
    score: Int,
    totalQuestions: Int,
    onRestartClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val accuracy = if (totalQuestions > 0) {
        (score.toFloat() / totalQuestions * 100).toInt()
    } else {
        0
    }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "练习完成！",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        Text(
            text = "得分: $score / $totalQuestions",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "正确率: $accuracy%",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 48.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Text("返回")
            }
            
            Button(
                onClick = onRestartClick,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Text("再来一次")
            }
        }
    }
}


