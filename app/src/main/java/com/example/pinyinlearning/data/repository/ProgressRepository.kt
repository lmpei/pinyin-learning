package com.example.pinyinlearning.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.pinyinlearning.data.model.Progress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject

/**
 * MVP版本：使用 SharedPreferences 简化存储
 * 后续可以升级为 DataStore
 */
class ProgressRepository(private val context: Context) {
    
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("pinyin_learning", Context.MODE_PRIVATE)
    
    private val _progress = MutableStateFlow(loadProgress())
    val progress: StateFlow<Progress> = _progress.asStateFlow()
    
    /**
     * 从 SharedPreferences 加载进度
     */
    private fun loadProgress(): Progress {
        val progressJson = prefs.getString("learning_progress", null)
        return if (progressJson != null) {
            try {
                parseProgressFromJson(progressJson)
            } catch (e: Exception) {
                Progress()
            }
        } else {
            Progress()
        }
    }
    
    /**
     * 保存学习进度
     */
    fun saveProgress(progress: Progress) {
        val json = progressToJson(progress)
        prefs.edit().putString("learning_progress", json).apply()
        _progress.value = progress
    }
    
    /**
     * 更新练习结果
     */
    fun updatePracticeResult(isCorrect: Boolean) {
        val currentProgress = _progress.value
        val updatedProgress = currentProgress.copy(
            practiceCount = currentProgress.practiceCount + 1,
            correctCount = if (isCorrect) currentProgress.correctCount + 1 else currentProgress.correctCount,
            lastUpdate = System.currentTimeMillis()
        )
        saveProgress(updatedProgress)
    }
    
    /**
     * 添加已学习的拼音
     */
    fun addLearnedPinyin(pinyinId: String) {
        val currentProgress = _progress.value
        val updatedProgress = currentProgress.addLearnedPinyin(pinyinId)
        saveProgress(updatedProgress)
    }
    
    /**
     * 将 Progress 转换为 JSON 字符串
     */
    private fun progressToJson(progress: Progress): String {
        val json = JSONObject()
        json.put("learnedPinyins", JSONArray(progress.learnedPinyins))
        json.put("practiceCount", progress.practiceCount)
        json.put("correctCount", progress.correctCount)
        json.put("lastUpdate", progress.lastUpdate)
        return json.toString()
    }
    
    /**
     * 从 JSON 字符串解析 Progress
     */
    private fun parseProgressFromJson(jsonString: String): Progress {
        val json = JSONObject(jsonString)
        val learnedPinyinsArray = json.getJSONArray("learnedPinyins")
        val learnedPinyins = mutableListOf<String>()
        for (i in 0 until learnedPinyinsArray.length()) {
            learnedPinyins.add(learnedPinyinsArray.getString(i))
        }
        return Progress(
            learnedPinyins = learnedPinyins,
            practiceCount = json.getInt("practiceCount"),
            correctCount = json.getInt("correctCount"),
            lastUpdate = json.getLong("lastUpdate")
        )
    }
}
