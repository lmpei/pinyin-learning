package com.example.pinyinlearning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pinyinlearning.di.ViewModelFactory
import com.example.pinyinlearning.ui.home.HomeScreen
import com.example.pinyinlearning.ui.pinyin.PinyinScreen
import com.example.pinyinlearning.ui.pinyin.PinyinViewModel
import com.example.pinyinlearning.ui.practice.PracticeScreen
import com.example.pinyinlearning.ui.practice.PracticeViewModel
import com.example.pinyinlearning.ui.progress.ProgressScreen
import com.example.pinyinlearning.ui.progress.ProgressViewModel
import com.example.pinyinlearning.ui.theme.PinyinLearningTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PinyinLearningTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PinyinLearningApp()
                }
            }
        }
    }
}

/**
 * 主应用导航
 */
@Composable
fun PinyinLearningApp() {
    val navController = rememberNavController()
    val viewModelFactory = ViewModelFactory(androidx.compose.ui.platform.LocalContext.current)
    
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToPinyin = { navController.navigate("pinyin") },
                onNavigateToPractice = { navController.navigate("practice") },
                onNavigateToProgress = { navController.navigate("progress") }
            )
        }
        
        composable("pinyin") {
            val viewModel: PinyinViewModel = viewModel(
                factory = viewModelFactory
            )
            PinyinScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("practice") {
            val viewModel: PracticeViewModel = viewModel(
                factory = viewModelFactory
            )
            PracticeScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("progress") {
            val viewModel: ProgressViewModel = viewModel(
                factory = viewModelFactory
            )
            ProgressScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}