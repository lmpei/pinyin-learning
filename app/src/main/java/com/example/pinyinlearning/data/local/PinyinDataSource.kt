package com.example.pinyinlearning.data.local

import com.example.pinyinlearning.data.model.Pinyin
import com.example.pinyinlearning.data.model.PinyinCategory

/**
 * 拼音数据源
 * 提供静态拼音数据
 */
object PinyinDataSource {
    
    /**
     * 声母列表
     */
    val shengmu = listOf(
        "b", "p", "m", "f", "d", "t", "n", "l",
        "g", "k", "h", "j", "q", "x",
        "zh", "ch", "sh", "r", "z", "c", "s",
        "y", "w"
    )
    
    /**
     * 韵母列表
     */
    val yunmu = listOf(
        "a", "o", "e", "i", "u", "ü",
        "ai", "ei", "ui", "ao", "ou", "iu", "ie", "üe", "er",
        "an", "en", "in", "un", "ün",
        "ang", "eng", "ing", "ong"
    )
    
    /**
     * 获取所有拼音数据
     */
    fun getAllPinyins(): List<Pinyin> {
        val pinyins = mutableListOf<Pinyin>()
        
        // 添加声母
        shengmu.forEachIndexed { index, text ->
            pinyins.add(
                Pinyin(
                    id = "sm_$index",
                    text = text,
                    tone = 0,
                    category = PinyinCategory.SHENGMU
                )
            )
        }
        
        // 添加韵母
        yunmu.forEachIndexed { index, text ->
            pinyins.add(
                Pinyin(
                    id = "ym_$index",
                    text = text,
                    tone = 0,
                    category = PinyinCategory.YUNMU
                )
            )
        }
        
        // 添加一些常用完整拼音示例（带声调）
        val fullPinyins = listOf(
            "ma", "ba", "pa", "fa", "da", "ta", "na", "la",
            "ga", "ka", "ha", "jia", "qia", "xia",
            "zha", "cha", "sha", "ra", "za", "ca", "sa",
            "yi", "wu", "yu"
        )
        
        fullPinyins.forEachIndexed { index, text ->
            // 为每个拼音添加不同声调的版本
            for (tone in 1..4) {
                pinyins.add(
                    Pinyin(
                        id = "full_${index}_$tone",
                        text = text,
                        tone = tone,
                        category = PinyinCategory.FULL
                    )
                )
            }
        }
        
        return pinyins
    }
    
    /**
     * 根据分类获取拼音
     */
    fun getPinyinsByCategory(category: PinyinCategory): List<Pinyin> {
        return getAllPinyins().filter { it.category == category }
    }
    
    /**
     * 根据ID获取拼音
     */
    fun getPinyinById(id: String): Pinyin? {
        return getAllPinyins().find { it.id == id }
    }
}

