# 快速开始指南

## 5分钟快速启动

### 第一步：安装开发环境

**必需软件**:
- Android Studio（最新稳定版）
- JDK 17+
- Android SDK（API Level 24+）

**下载地址**:
- Android Studio: https://developer.android.com/studio
- 安装时选择包含Android SDK和模拟器

### 第二步：创建Android项目

1. 打开Android Studio
2. 选择 "New Project"
3. 选择模板：
   - **Empty Activity**（推荐，最简洁）
   - 或 **Empty Compose Activity**（如使用Compose）
4. 配置项目：
   - Name: `PinyinLearning`
   - Package name: `com.example.pinyinlearning`
   - Language: **Kotlin**（推荐）
   - Minimum SDK: **API 24** (Android 7.0)
   - Build configuration: Gradle (Kotlin)

### 第三步：配置项目依赖

在 `app/build.gradle.kts` 中添加依赖：

```kotlin
dependencies {
    // Compose UI（如使用）
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    
    // 或传统View系统
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    
    // 架构组件
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    
    // 数据存储
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    
    // JSON处理
    implementation("com.google.code.gson:gson:2.10.1")
}
```

### 第四步：创建基础结构

```
app/src/main/java/com/example/pinyinlearning/
├── ui/
│   ├── MainActivity.kt
│   └── main/
│       └── MainViewModel.kt
├── data/
│   ├── model/
│   │   └── Pinyin.kt
│   └── repository/
│       └── PinyinRepository.kt
├── service/
│   └── AudioService.kt
└── util/
    └── Constants.kt
```

### 第五步：实现核心功能

#### 1. 拼音数据模型（data/model/Pinyin.kt）

```kotlin
data class Pinyin(
    val id: String,
    val text: String,      // 如: "ma"
    val tone: Int,         // 声调: 1-4
    val category: PinyinCategory,
    val audioResId: Int? = null
)

enum class PinyinCategory {
    SHENGMU, YUNMU, FULL
}
```

#### 2. 拼音数据（data/repository/PinyinRepository.kt）

```kotlin
class PinyinRepository {
    fun getAllPinyins(): List<Pinyin> {
        return listOf(
            Pinyin("ma1", "ma", 1, PinyinCategory.FULL, R.raw.ma1),
            Pinyin("ba1", "ba", 1, PinyinCategory.FULL, R.raw.ba1),
            // ... 更多拼音
        )
    }
}
```

#### 3. 音频播放服务（service/AudioService.kt）

```kotlin
class AudioService(private val context: Context) {
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .build()
    
    fun playPinyin(audioResId: Int) {
        soundPool.load(context, audioResId, 1)
        soundPool.setOnLoadCompleteListener { _, sampleId, _ ->
            soundPool.play(sampleId, 1f, 1f, 1, 0, 1f)
        }
    }
}
```

#### 4. 拼音卡片组件（Compose示例）

```kotlin
@Composable
fun PinyinCard(
    pinyin: Pinyin,
    onPlay: () -> Unit
) {
    Card(
        onClick = onPlay,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = pinyin.text,
                style = MaterialTheme.typography.h4
            )
            Text(
                text = "声调: ${pinyin.tone}",
                style = MaterialTheme.typography.body2
            )
        }
    }
}
```

### 第六步：运行项目

1. 连接Android设备或启动模拟器
2. 点击Android Studio的 **Run** 按钮（绿色三角形）
3. 或按快捷键 **Shift + F10**

## MVP开发优先级

### Day 1: 基础框架
- [x] 项目初始化
- [ ] 创建基础Activity/Fragment
- [ ] 设置Navigation
- [ ] 拼音数据准备

### Day 2: 核心功能
- [ ] 拼音展示页面
- [ ] 音频播放功能
- [ ] 基础UI设计

### Day 3: 练习功能
- [ ] 选择题生成
- [ ] 答案验证
- [ ] 反馈显示

### Day 4: 完善与发布
- [ ] 进度保存
- [ ] 测试与优化
- [ ] 构建APK

## 关键代码片段

### Navigation配置

```kotlin
// res/navigation/nav_graph.xml
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/nav_graph"
    app:startDestination="@id/mainFragment">
    
    <fragment
        android:id="@+id/mainFragment"
        android:name="com.example.pinyinlearning.ui.main.MainFragment"
        android:label="拼音学习" />
    
    <fragment
        android:id="@+id/practiceFragment"
        android:name="com.example.pinyinlearning.ui.practice.PracticeFragment"
        android:label="练习" />
</navigation>
```

### ViewModel示例

```kotlin
class PinyinViewModel : ViewModel() {
    private val repository = PinyinRepository()
    
    private val _pinyins = MutableLiveData<List<Pinyin>>()
    val pinyins: LiveData<List<Pinyin>> = _pinyins
    
    init {
        loadPinyins()
    }
    
    private fun loadPinyins() {
        _pinyins.value = repository.getAllPinyins()
    }
}
```

### 数据存储（DataStore）

```kotlin
class ProgressRepository(private val context: Context) {
    private val dataStore = context.createDataStore("progress_prefs")
    
    suspend fun saveProgress(progress: Progress) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("progress")] = 
                Gson().toJson(progress)
        }
    }
    
    suspend fun getProgress(): Flow<Progress?> {
        return dataStore.data.map { preferences ->
            val json = preferences[stringPreferencesKey("progress")]
            Gson().fromJson(json, Progress::class.java)
        }
    }
}
```

## 音频资源准备

### 方案1: 使用TTS（文本转语音）

```kotlin
class TTSService(private val context: Context) {
    private var tts: TextToSpeech? = null
    
    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.CHINESE
            }
        }
    }
    
    fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}
```

### 方案2: 预录音频文件
- 准备MP3音频文件
- 放在 `res/raw/` 目录
- 按拼音ID命名（如：ma1.mp3）
- 在代码中使用 `R.raw.ma1` 引用

## 常见问题速查

**Q: 音频无法播放？**  
A: 检查音频文件是否在res/raw目录，确保资源ID正确

**Q: 应用崩溃？**  
A: 查看Logcat日志，检查空指针、权限等问题

**Q: 布局显示异常？**  
A: 检查布局文件或Compose代码，确保约束正确

**Q: 数据存储不生效？**  
A: DataStore是异步的，确保在协程中调用

## 下一步

- 查看[需求文档](./requirements.md)了解详细功能
- 查看[开发指南](./development.md)了解开发规范
- 查看[部署文档](./deployment.md)了解APK构建流程

## 获取帮助

- 查看各模块文档
- 检查代码注释
- 参考Android官方文档：https://developer.android.com
