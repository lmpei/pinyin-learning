package com.example.pinyinlearning.data.local

import com.example.pinyinlearning.data.model.Question
import com.example.pinyinlearning.data.model.QuestionType

/**
 * 题目数据源
 * 提供练习题目数据
 */
object QuestionDataSource {
    
    /**
     * 获取所有题目
     */
    fun getAllQuestions(): List<Question> {
        return listOf(
            // 选择题示例
            Question(
                id = "q1",
                type = QuestionType.CHOICE,
                question = "请选择拼音 'ma'（第一声）",
                options = listOf("mā", "má", "mǎ", "mà"),
                correctAnswer = "mā"
            ),
            Question(
                id = "q2",
                type = QuestionType.CHOICE,
                question = "请选择拼音 'ba'（第四声）",
                options = listOf("bā", "bá", "bǎ", "bà"),
                correctAnswer = "bà"
            ),
            Question(
                id = "q3",
                type = QuestionType.CHOICE,
                question = "请选择拼音 'yi'（第一声）",
                options = listOf("yī", "yí", "yǐ", "yì"),
                correctAnswer = "yī"
            ),
            Question(
                id = "q4",
                type = QuestionType.SPELL,
                question = "请选择 '妈' 的拼音",
                options = listOf("mā", "má", "mǎ", "mà"),
                correctAnswer = "mā"
            ),
            Question(
                id = "q5",
                type = QuestionType.SPELL,
                question = "请选择 '爸' 的拼音",
                options = listOf("bā", "bá", "bǎ", "bà"),
                correctAnswer = "bà"
            )
        )
    }
    
    /**
     * 随机获取指定数量的题目
     */
    fun getRandomQuestions(count: Int): List<Question> {
        val allQuestions = getAllQuestions()
        return allQuestions.shuffled().take(count)
    }
}

