package com.example.pinyinlearning.data.model

/**
 * 题目类型
 */
enum class QuestionType {
    CHOICE,  // 选择题：听音选拼音
    SPELL    // 拼写题：看字选拼音
}

/**
 * 练习题目数据模型
 */
data class Question(
    val id: String,
    val type: QuestionType,
    val question: String,           // 题目文本
    val options: List<String>,       // 选项列表
    val correctAnswer: String,       // 正确答案
    val audioResId: Int? = null      // 音频资源ID（用于听音选拼音）
) {
    /**
     * 检查答案是否正确
     */
    fun checkAnswer(selectedAnswer: String): Boolean {
        return selectedAnswer == correctAnswer
    }
}

