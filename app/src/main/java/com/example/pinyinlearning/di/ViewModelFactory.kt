package com.example.pinyinlearning.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pinyinlearning.data.repository.PinyinRepository
import com.example.pinyinlearning.data.repository.ProgressRepository
import com.example.pinyinlearning.data.repository.QuestionRepository
import com.example.pinyinlearning.service.AudioService
import com.example.pinyinlearning.ui.pinyin.PinyinViewModel
import com.example.pinyinlearning.ui.practice.PracticeViewModel
import com.example.pinyinlearning.ui.progress.ProgressViewModel

/**
 * ViewModel Factory
 */
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    
    private val pinyinRepository = PinyinRepository()
    private val questionRepository = QuestionRepository()
    private val progressRepository = ProgressRepository(context)
    private val audioService = AudioService(context)
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PinyinViewModel::class.java) -> {
                PinyinViewModel(pinyinRepository, progressRepository, audioService) as T
            }
            modelClass.isAssignableFrom(PracticeViewModel::class.java) -> {
                PracticeViewModel(questionRepository, progressRepository, audioService) as T
            }
            modelClass.isAssignableFrom(ProgressViewModel::class.java) -> {
                ProgressViewModel(progressRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

