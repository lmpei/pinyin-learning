# API设计文档

## 1. 概述

本文档描述拼音学习应用的API设计。由于MVP版本主要使用本地数据和存储，大部分功能不依赖后端API。本文档包含：
- 本地服务接口设计
- 未来扩展的API设计（如需要）

## 2. 本地服务接口

### 2.1 拼音数据服务

#### 获取所有拼音
```kotlin
// data/repository/PinyinRepository.kt
class PinyinRepository {
    /**
     * 获取所有拼音数据
     * @param category 分类筛选：SHENGMU | YUNMU | FULL | null（全部）
     * @return 拼音列表
     */
    fun getAllPinyins(category: PinyinCategory? = null): List<Pinyin>
    
    /**
     * 根据ID获取拼音
     * @param id 拼音ID
     * @return 拼音对象，不存在返回null
     */
    fun getPinyinById(id: String): Pinyin?
    
    /**
     * 搜索拼音
     * @param keyword 关键词
     * @return 匹配的拼音列表
     */
    fun searchPinyins(keyword: String): List<Pinyin>
}
```

#### 使用示例
```kotlin
val repository = PinyinRepository()

// 获取所有完整拼音
val fullPinyins = repository.getAllPinyins(PinyinCategory.FULL)

// 获取特定拼音
val pinyin = repository.getPinyinById("ma1")

// 搜索拼音
val results = repository.searchPinyins("ma")
```

### 2.2 音频服务

#### 播放音频
```kotlin
// service/AudioService.kt
class AudioService(private val context: Context) {
    /**
     * 播放拼音发音
     * @param audioResId 音频资源ID（R.raw.xxx）
     */
    fun playPinyin(audioResId: Int)
    
    /**
     * 停止当前播放
     */
    fun stop()
    
    /**
     * 设置音量
     * @param volume 音量 0.0-1.0
     */
    fun setVolume(volume: Float)
    
    /**
     * 释放资源（在Activity/Fragment销毁时调用）
     */
    fun release()
}
```

#### 使用示例
```kotlin
val audioService = AudioService(context)

// 播放拼音
audioService.playPinyin(R.raw.ma1)

// 设置音量
audioService.setVolume(0.8f)

// 释放资源
override fun onDestroy() {
    super.onDestroy()
    audioService.release()
}
```

### 2.3 存储服务

#### 进度管理
```kotlin
// data/repository/ProgressRepository.kt
class ProgressRepository(private val context: Context) {
    /**
     * 保存学习进度
     * @param progress 进度对象
     */
    suspend fun saveProgress(progress: Progress)
    
    /**
     * 获取学习进度
     * @return Flow<Progress?> 进度数据流
     */
    fun getProgress(): Flow<Progress?>
    
    /**
     * 更新已学习的拼音
     * @param pinyinId 拼音ID
     */
    suspend fun markAsLearned(pinyinId: String)
    
    /**
     * 清除所有进度
     */
    suspend fun clearProgress()
}
```

#### 使用示例
```kotlin
val repository = ProgressRepository(context)

// 保存进度（在协程中）
viewModelScope.launch {
    repository.saveProgress(
        Progress(
            learnedPinyins = listOf("ma1", "ba1"),
            practiceCount = 10,
            correctCount = 8
        )
    )
}

// 获取进度（观察Flow）
viewModelScope.launch {
    repository.getProgress().collect { progress ->
        _progress.value = progress
    }
}
```

### 2.4 练习服务

#### 生成题目
```kotlin
// service/PracticeService.kt
class PracticeService {
    /**
     * 生成选择题
     * @param count 题目数量
     * @param difficulty 难度：EASY | MEDIUM | HARD
     * @return 题目列表
     */
    fun generateChoiceQuestions(
        count: Int,
        difficulty: Difficulty = Difficulty.EASY
    ): List<Question>
    
    /**
     * 验证答案
     * @param questionId 题目ID
     * @param answer 用户答案
     * @return 是否正确
     */
    fun checkAnswer(questionId: String, answer: String): Boolean
    
    /**
     * 获取练习统计
     * @return 统计信息
     */
    fun getStatistics(): PracticeStatistics
}

enum class Difficulty {
    EASY, MEDIUM, HARD
}
```

#### 使用示例
```kotlin
val practiceService = PracticeService()

// 生成5道简单题目
val questions = practiceService.generateChoiceQuestions(5, Difficulty.EASY)

// 检查答案
val isCorrect = practiceService.checkAnswer("q1", "ma1")
```

## 3. 数据模型

### 3.1 Pinyin（拼音）
```kotlin
data class Pinyin(
    val id: String,              // 唯一标识，如 "ma1"
    val text: String,            // 拼音文本，如 "ma"
    val tone: Int,               // 声调：1-4
    val category: PinyinCategory, // 分类
    val audioResId: Int? = null,  // 音频资源ID（R.raw.xxx）
    val example: String? = null   // 示例汉字（可选）
)

enum class PinyinCategory {
    SHENGMU, YUNMU, FULL
}
```

### 3.2 Question（题目）
```kotlin
data class Question(
    val id: String,              // 题目ID
    val type: QuestionType,      // 题目类型
    val question: String,        // 题目文本
    val options: List<String>,   // 选项（选择题）
    val correctAnswer: String,   // 正确答案
    val audioResId: Int? = null, // 音频资源ID（听音题）
    val explanation: String? = null // 解释（可选）
)

enum class QuestionType {
    CHOICE, SPELL
}
```

### 3.3 Progress（进度）
```kotlin
data class Progress(
    val learnedPinyins: List<String>, // 已学习的拼音ID列表
    val practiceCount: Int,          // 练习次数
    val correctCount: Int,             // 正确次数
    val lastUpdate: Long,             // 最后更新时间（时间戳）
    val dailyGoal: Int? = null        // 每日目标（可选）
)
```

### 3.4 PracticeStatistics（练习统计）
```kotlin
data class PracticeStatistics(
    val totalQuestions: Int,     // 总题数
    val correctAnswers: Int,      // 正确答案数
    val accuracy: Float,          // 准确率（0.0-1.0）
    val averageTime: Long        // 平均答题时间（毫秒）
)
```

## 4. 错误处理

### 4.1 错误类型
```kotlin
sealed class AppError : Exception() {
    object AudioPlayFailed : AppError()
    object StorageError : AppError()
    object DataNotFound : AppError()
    object InvalidInput : AppError()
}
```

### 4.2 错误处理示例
```kotlin
try {
    audioService.playPinyin(R.raw.ma1)
} catch (e: AppError.AudioPlayFailed) {
    // 处理音频播放失败
    Log.e("AudioService", "音频播放失败，请检查音频文件")
    // 显示用户提示
    Toast.makeText(context, "音频播放失败", Toast.LENGTH_SHORT).show()
} catch (e: Exception) {
    Log.e("AudioService", "未知错误", e)
}
```

## 5. 未来扩展API（可选）

### 5.1 用户认证（如需要）
```
POST /api/auth/login
POST /api/auth/register
POST /api/auth/logout
GET  /api/auth/me
```

### 5.2 云端同步（如需要）
```
GET  /api/progress          # 获取云端进度
POST /api/progress          # 同步进度到云端
PUT  /api/progress          # 更新进度
```

### 5.3 学习报告（如需要）
```
GET  /api/reports/daily     # 每日报告
GET  /api/reports/weekly    # 每周报告
GET  /api/statistics        # 统计数据
```

## 6. API调用示例

### 6.1 完整使用流程
```kotlin
// 1. 获取拼音数据
val repository = PinyinRepository()
val pinyins = repository.getAllPinyins(PinyinCategory.FULL)

// 2. 播放发音
val audioService = AudioService(context)
audioService.playPinyin(R.raw.ma1)

// 3. 生成练习
val practiceService = PracticeService()
val questions = practiceService.generateChoiceQuestions(5, Difficulty.EASY)

// 4. 检查答案
val isCorrect = practiceService.checkAnswer("q1", "ma1")

// 5. 保存进度
val progressRepo = ProgressRepository(context)
viewModelScope.launch {
    progressRepo.markAsLearned("ma1")
    val progress = progressRepo.getProgress().first()
}
```

## 7. 接口文档维护

- 所有接口变更需更新本文档
- 新增接口需提供使用示例
- 废弃接口需标注并说明替代方案

