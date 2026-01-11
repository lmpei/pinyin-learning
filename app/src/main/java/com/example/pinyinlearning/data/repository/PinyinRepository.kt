package com.example.pinyinlearning.data.repository

import com.example.pinyinlearning.data.local.PinyinDataSource
import com.example.pinyinlearning.data.model.Pinyin
import com.example.pinyinlearning.data.model.PinyinCategory

/**
 * 拼音数据仓库
 */
class PinyinRepository {
    
    private val dataSource = PinyinDataSource
    
    /**
     * 获取所有拼音
     */
    fun getAllPinyins(): List<Pinyin> {
        return dataSource.getAllPinyins()
    }
    
    /**
     * 根据分类获取拼音
     */
    fun getPinyinsByCategory(category: PinyinCategory): List<Pinyin> {
        return dataSource.getPinyinsByCategory(category)
    }
    
    /**
     * 根据ID获取拼音
     */
    fun getPinyinById(id: String): Pinyin? {
        return dataSource.getPinyinById(id)
    }
    
    /**
     * 搜索拼音
     */
    fun searchPinyins(query: String): List<Pinyin> {
        val allPinyins = getAllPinyins()
        return if (query.isBlank()) {
            allPinyins
        } else {
            allPinyins.filter {
                it.text.contains(query, ignoreCase = true) ||
                it.id.contains(query, ignoreCase = true)
            }
        }
    }
}

