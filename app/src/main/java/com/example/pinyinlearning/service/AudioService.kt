package com.example.pinyinlearning.service

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.Locale

/**
 * 音频播放服务
 * MVP版本：使用 TextToSpeech (TTS) 播放拼音发音
 * 简单实用，无需预录音频文件
 */
class AudioService(private val context: Context) {
    
    private var tts: TextToSpeech? = null
    private var isInitialized = false
    
    init {
        initializeTTS()
    }
    
    /**
     * 初始化 TTS
     */
    private fun initializeTTS() {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale.CHINESE)
                isInitialized = result != TextToSpeech.LANG_MISSING_DATA && 
                               result != TextToSpeech.LANG_NOT_SUPPORTED
            }
        }
    }
    
    /**
     * 播放拼音发音
     * @param pinyinText 拼音文本，如 "ma", "ba" 等
     */
    fun playPinyin(pinyinText: String) {
        if (!isInitialized || tts == null) {
            // 如果TTS未初始化，尝试重新初始化
            initializeTTS()
            return
        }
        
        // 使用TTS播放拼音
        tts?.speak(pinyinText, TextToSpeech.QUEUE_FLUSH, null, pinyinText)
    }
    
    /**
     * 释放资源
     */
    fun release() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        isInitialized = false
    }
}

