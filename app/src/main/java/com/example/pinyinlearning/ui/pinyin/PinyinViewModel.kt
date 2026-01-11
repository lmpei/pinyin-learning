package com.example.pinyinlearning.ui.pinyin

import androidx.lifecycle.ViewModel
import com.example.pinyinlearning.data.model.Pinyin
import com.example.pinyinlearning.data.model.PinyinCategory
import com.example.pinyinlearning.data.repository.PinyinRepository
import com.example.pinyinlearning.data.repository.ProgressRepository
import com.example.pinyinlearning.service.AudioService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 拼音展示页面的 ViewModel
 */
class PinyinViewModel(
    private val pinyinRepository: PinyinRepository,
    private val progressRepository: ProgressRepository,
    private val audioService: AudioService
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PinyinUiState())
    val uiState: StateFlow<PinyinUiState> = _uiState.asStateFlow()
    
    init {
        loadPinyins()
    }
    
    /**
     * 加载拼音数据
     */
    private fun loadPinyins() {
        val allPinyins = pinyinRepository.getAllPinyins()
        _uiState.value = _uiState.value.copy(
            allPinyins = allPinyins,
            filteredPinyins = allPinyins
        )
    }
    
    /**
     * 根据分类筛选拼音
     */
    fun filterByCategory(category: PinyinCategory?) {
        val filtered = if (category != null) {
            pinyinRepository.getPinyinsByCategory(category)
        } else {
            pinyinRepository.getAllPinyins()
        }
        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            filteredPinyins = filtered
        )
    }
    
    /**
     * 搜索拼音
     */
    fun searchPinyins(query: String) {
        val filtered = pinyinRepository.searchPinyins(query)
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            filteredPinyins = filtered
        )
    }
    
    /**
     * 播放拼音发音
     */
    fun playPinyin(pinyin: Pinyin) {
        // 使用 TTS 播放拼音文本
        audioService.playPinyin(pinyin.text)
        // 标记为已学习
        progressRepository.addLearnedPinyin(pinyin.id)
    }
}

/**
 * 拼音页面 UI 状态
 */
data class PinyinUiState(
    val allPinyins: List<Pinyin> = emptyList(),
    val filteredPinyins: List<Pinyin> = emptyList(),
    val selectedCategory: PinyinCategory? = null,
    val searchQuery: String = ""
)

