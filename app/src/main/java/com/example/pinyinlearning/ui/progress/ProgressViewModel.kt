package com.example.pinyinlearning.ui.progress

import androidx.lifecycle.ViewModel
import com.example.pinyinlearning.data.repository.ProgressRepository
import kotlinx.coroutines.flow.StateFlow

/**
 * 进度页面的 ViewModel
 */
class ProgressViewModel(
    private val progressRepository: ProgressRepository
) : ViewModel() {
    
    /**
     * 学习进度
     */
    val progress: StateFlow<com.example.pinyinlearning.data.model.Progress> = 
        progressRepository.progress
}

