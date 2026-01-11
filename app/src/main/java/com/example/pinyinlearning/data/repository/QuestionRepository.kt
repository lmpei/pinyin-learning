package com.example.pinyinlearning.data.repository

import com.example.pinyinlearning.data.local.QuestionDataSource
import com.example.pinyinlearning.data.model.Question

/**
 * 题目数据仓库
 */
class QuestionRepository {
    
    private val dataSource = QuestionDataSource
    
    /**
     * 获取所有题目
     */
    fun getAllQuestions(): List<Question> {
        return dataSource.getAllQuestions()
    }
    
    /**
     * 随机获取指定数量的题目
     */
    fun getRandomQuestions(count: Int): List<Question> {
        return dataSource.getRandomQuestions(count)
    }
}

