package com.example.pinyinlearning.ui.practice

import androidx.lifecycle.ViewModel
import com.example.pinyinlearning.data.model.Question
import com.example.pinyinlearning.data.repository.QuestionRepository
import com.example.pinyinlearning.data.repository.ProgressRepository
import com.example.pinyinlearning.service.AudioService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 练习页面的 ViewModel
 */
class PracticeViewModel(
    private val questionRepository: QuestionRepository,
    private val progressRepository: ProgressRepository,
    private val audioService: AudioService
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PracticeUiState())
    val uiState: StateFlow<PracticeUiState> = _uiState.asStateFlow()
    
    /**
     * 开始新的练习
     */
    fun startPractice(questionCount: Int = 5) {
        val questions = questionRepository.getRandomQuestions(questionCount)
        if (questions.isNotEmpty()) {
            _uiState.value = PracticeUiState(
                questions = questions,
                currentQuestionIndex = 0,
                currentQuestion = questions[0],
                selectedAnswer = null,
                showResult = false,
                isCorrect = false,
                score = 0,
                totalQuestions = questions.size
            )
            // 播放第一题的音频（如果是选择题）
            playCurrentQuestionAudio()
        }
    }
    
    /**
     * 选择答案
     */
    fun selectAnswer(answer: String) {
        val currentState = _uiState.value
        val currentQuestion = currentState.currentQuestion ?: return
        
        val isCorrect = currentQuestion.checkAnswer(answer)
        
        _uiState.value = currentState.copy(
            selectedAnswer = answer,
            showResult = true,
            isCorrect = isCorrect,
            score = if (isCorrect) currentState.score + 1 else currentState.score
        )
        
        // 更新进度
        progressRepository.updatePracticeResult(isCorrect)
    }
    
    /**
     * 下一题
     */
    fun nextQuestion() {
        val currentState = _uiState.value
        val nextIndex = currentState.currentQuestionIndex + 1
        
        if (nextIndex < currentState.questions.size) {
            _uiState.value = currentState.copy(
                currentQuestionIndex = nextIndex,
                currentQuestion = currentState.questions[nextIndex],
                selectedAnswer = null,
                showResult = false,
                isCorrect = false
            )
            playCurrentQuestionAudio()
        } else {
            // 练习完成
            _uiState.value = currentState.copy(
                isFinished = true
            )
        }
    }
    
    /**
     * 重新开始
     */
    fun restart() {
        startPractice(_uiState.value.totalQuestions)
    }
    
    /**
     * 播放当前题目的音频
     * MVP版本：使用TTS播放题目文本
     */
    fun playCurrentQuestionAudio() {
        val currentQuestion = _uiState.value.currentQuestion
        // 如果是选择题，播放题目文本；如果是拼写题，播放问题中的拼音
        val textToSpeak = currentQuestion?.question ?: ""
        if (textToSpeak.isNotEmpty()) {
            audioService.playPinyin(textToSpeak)
        }
    }
}

/**
 * 练习页面 UI 状态
 */
data class PracticeUiState(
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val currentQuestion: Question? = null,
    val selectedAnswer: String? = null,
    val showResult: Boolean = false,
    val isCorrect: Boolean = false,
    val score: Int = 0,
    val totalQuestions: Int = 0,
    val isFinished: Boolean = false
) {
    val progress: Float
        get() = if (totalQuestions > 0) {
            (currentQuestionIndex + 1).toFloat() / totalQuestions
        } else {
            0f
        }
}

