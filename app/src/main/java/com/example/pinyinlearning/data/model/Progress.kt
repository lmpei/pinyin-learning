package com.example.pinyinlearning.data.model

/**
 * 学习进度数据模型
 */
data class Progress(
    val learnedPinyins: List<String> = emptyList(),  // 已学习的拼音ID列表
    val practiceCount: Int = 0,                       // 练习次数
    val correctCount: Int = 0,                        // 正确次数
    val lastUpdate: Long = System.currentTimeMillis() // 最后更新时间
) {
    /**
     * 计算正确率
     */
    fun getAccuracy(): Float {
        return if (practiceCount > 0) {
            correctCount.toFloat() / practiceCount
        } else {
            0f
        }
    }
    
    /**
     * 检查拼音是否已学习
     */
    fun isLearned(pinyinId: String): Boolean {
        return learnedPinyins.contains(pinyinId)
    }
    
    /**
     * 添加已学习的拼音
     */
    fun addLearnedPinyin(pinyinId: String): Progress {
        val newList = if (learnedPinyins.contains(pinyinId)) {
            learnedPinyins
        } else {
            learnedPinyins + pinyinId
        }
        return copy(learnedPinyins = newList, lastUpdate = System.currentTimeMillis())
    }
}

