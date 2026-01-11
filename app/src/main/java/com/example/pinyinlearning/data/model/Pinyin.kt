package com.example.pinyinlearning.data.model

/**
 * 拼音数据模型
 */
data class Pinyin(
    val id: String,
    val text: String,              // 拼音文本，如: "ma"
    val tone: Int,                 // 声调: 0(轻声), 1(一声), 2(二声), 3(三声), 4(四声)
    val category: PinyinCategory,  // 分类
    val audioResId: Int? = null     // 音频资源ID（可选，后续添加音频文件时使用）
) {
    /**
     * 获取带声调的拼音文本
     */
    fun getTextWithTone(): String {
        if (tone == 0 || text.isEmpty()) return text
        
        // 简单的声调标记（实际应用中可以使用更复杂的规则）
        val toneMarks = mapOf(
            1 to "āáǎà",
            2 to "ēéěè",
            3 to "īíǐì",
            4 to "ōóǒò",
            5 to "ūúǔù",
            6 to "ǖǘǚǜ"
        )
        
        // 这里简化处理，实际应该根据拼音规则添加声调
        return when (tone) {
            1 -> text.replace('a', 'ā').replace('e', 'ē').replace('i', 'ī').replace('o', 'ō').replace('u', 'ū')
            2 -> text.replace('a', 'á').replace('e', 'é').replace('i', 'í').replace('o', 'ó').replace('u', 'ú')
            3 -> text.replace('a', 'ǎ').replace('e', 'ě').replace('i', 'ǐ').replace('o', 'ǒ').replace('u', 'ǔ')
            4 -> text.replace('a', 'à').replace('e', 'è').replace('i', 'ì').replace('o', 'ò').replace('u', 'ù')
            else -> text
        }
    }
}

