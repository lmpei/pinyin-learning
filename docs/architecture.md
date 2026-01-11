# 技术架构文档

## 1. 技术选型

### 1.1 Android技术栈（推荐）

#### 开发语言
- **Kotlin**（强烈推荐）- 现代、简洁、安全
- **Java**（备选）- 如团队更熟悉Java

#### UI框架
- **Jetpack Compose**（推荐）- 现代声明式UI，开发效率高
- **传统View系统**（备选）- 如需要兼容旧代码或团队更熟悉

#### 架构组件
- **MVVM架构模式**
- **ViewModel** - 管理UI相关数据
- **LiveData / StateFlow** - 响应式数据流
- **Navigation Component** - 页面导航

#### 数据存储
- **SharedPreferences** - 简单键值对存储（适合进度数据）
- **DataStore**（推荐）- 更现代的替代方案，支持类型安全
- **Room数据库**（如需要复杂数据）- 本地SQLite数据库

#### 音频播放
- **MediaPlayer** - 播放完整音频文件
- **SoundPool** - 播放短音频（推荐用于拼音发音）
- **TextToSpeech (TTS)** - 文本转语音（备选方案）

### 1.2 技术栈详细配置

```
Android技术栈:
├── 语言: Kotlin 1.9+
├── 最低SDK: API 24 (Android 7.0)
├── 目标SDK: API 34 (Android 14)
├── UI: Jetpack Compose 或 View系统
├── 架构: MVVM
├── 状态管理: ViewModel + StateFlow/LiveData
├── 导航: Navigation Component
├── 存储: DataStore / SharedPreferences
├── 音频: SoundPool / MediaPlayer
└── 构建: Gradle + Android Studio
```

### 1.3 核心依赖

```gradle
// build.gradle (Module: app)

dependencies {
    // Compose (如使用)
    implementation "androidx.compose.ui:ui:$compose_version"
    implementation "androidx.compose.material:material:$compose_version"
    implementation "androidx.compose.ui:ui-tooling-preview:$compose_version"
    
    // 或传统View系统
    implementation "androidx.appcompat:appcompat:1.6.1"
    implementation "com.google.android.material:material:1.10.0"
    
    // 架构组件
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.6.2"
    implementation "androidx.navigation:navigation-fragment-ktx:2.7.5"
    implementation "androidx.navigation:navigation-ui-ktx:2.7.5"
    
    // 数据存储
    implementation "androidx.datastore:datastore-preferences:1.0.0"
    
    // 音频播放
    // SoundPool和MediaPlayer是系统API，无需额外依赖
}
```

## 2. 系统架构

### 2.1 整体架构

```
┌─────────────────────────────────────┐
│           用户界面层 (UI)            │
│  ┌──────────┐  ┌──────────┐        │
│  │ 拼音展示 │  │ 练习模块 │        │
│  └──────────┘  └──────────┘        │
└─────────────────────────────────────┘
           ↓              ↓
┌─────────────────────────────────────┐
│         业务逻辑层 (Logic)           │
│  ┌──────────┐  ┌──────────┐        │
│  │ 发音服务 │  │ 进度管理 │        │
│  └──────────┘  └──────────┘        │
└─────────────────────────────────────┘
           ↓              ↓
┌─────────────────────────────────────┐
│         数据层 (Data)                │
│  ┌──────────┐  ┌──────────┐        │
│  │ 拼音数据 │  │ 本地存储 │        │
│  └──────────┘  └──────────┘        │
└─────────────────────────────────────┘
```

### 2.2 模块划分

#### 模块1: 拼音数据模块
- **职责**: 管理拼音数据（声母、韵母、完整拼音）
- **数据结构**:
```kotlin
data class Pinyin(
    val id: String,
    val text: String,      // 如: "ma"
    val tone: Int,         // 声调: 1-4
    val category: PinyinCategory, // 'shengmu' | 'yunmu' | 'full'
    val audioResId: Int? = null   // 音频资源ID
)

enum class PinyinCategory {
    SHENGMU, YUNMU, FULL
}
```

#### 模块2: 发音服务模块
- **职责**: 处理音频播放
- **实现方式**:
  - SoundPool播放预录音频文件（推荐，适合短音频）
  - MediaPlayer播放完整音频
  - TextToSpeech（TTS）备选方案

#### 模块3: 练习模块
- **职责**: 生成题目、验证答案
- **数据结构**:
```kotlin
data class Question(
    val id: String,
    val type: QuestionType, // CHOICE | SPELL
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    val audioResId: Int? = null
)

enum class QuestionType {
    CHOICE, SPELL
}
```

#### 模块4: 进度管理模块
- **职责**: 记录学习进度
- **存储**: DataStore / SharedPreferences
- **数据结构**:
```kotlin
data class Progress(
    val learnedPinyins: List<String>,
    val practiceCount: Int,
    val correctCount: Int,
    val lastUpdate: Long // 时间戳
)
```

## 3. 数据设计

### 3.1 拼音数据

```kotlin
// 声母数据
val shengmu = listOf("b", "p", "m", "f", "d", "t", "n", "l", ...)

// 韵母数据
val yunmu = listOf("a", "o", "e", "i", "u", "ü", "ai", "ei", ...)

// 完整拼音示例
val pinyins = listOf(
    Pinyin("ma1", "ma", 1, PinyinCategory.FULL, R.raw.ma1),
    Pinyin("ba4", "ba", 4, PinyinCategory.FULL, R.raw.ba4),
    // ...
)
```

### 3.2 存储结构

```kotlin
// DataStore Keys
object PreferencesKeys {
    val PROGRESS = stringPreferencesKey("pinyin_learning_progress")
    val SETTINGS = stringPreferencesKey("pinyin_learning_settings")
}

// 或使用SharedPreferences
object StorageKeys {
    const val PROGRESS = "pinyin_learning_progress"
    const val SETTINGS = "pinyin_learning_settings"
}
```

## 4. 目录结构

```
app/src/main/
├── java/com/example/pinyinlearning/
│   ├── ui/                      # UI层
│   │   ├── main/               # 主页面
│   │   │   ├── MainActivity.kt
│   │   │   └── MainViewModel.kt
│   │   ├── pinyin/             # 拼音展示页面
│   │   │   ├── PinyinFragment.kt / PinyinScreen.kt
│   │   │   └── PinyinViewModel.kt
│   │   ├── practice/           # 练习页面
│   │   │   ├── PracticeFragment.kt / PracticeScreen.kt
│   │   │   └── PracticeViewModel.kt
│   │   └── progress/           # 进度页面
│   │       └── ProgressFragment.kt / ProgressScreen.kt
│   ├── data/                    # 数据层
│   │   ├── model/              # 数据模型
│   │   │   ├── Pinyin.kt
│   │   │   ├── Question.kt
│   │   │   └── Progress.kt
│   │   ├── repository/         # 数据仓库
│   │   │   ├── PinyinRepository.kt
│   │   │   └── ProgressRepository.kt
│   │   └── local/             # 本地数据源
│   │       ├── PinyinDataSource.kt
│   │       └── ProgressDataSource.kt
│   ├── service/                # 服务层
│   │   ├── AudioService.kt     # 音频服务
│   │   └── StorageService.kt   # 存储服务
│   ├── util/                   # 工具类
│   │   ├── Constants.kt       # 常量
│   │   └── Extensions.kt       # 扩展函数
│   └── di/                     # 依赖注入（可选）
│       └── AppModule.kt
├── res/                        # 资源文件
│   ├── layout/                # 布局文件（如使用View系统）
│   ├── drawable/              # 图片资源
│   ├── raw/                   # 音频文件
│   ├── values/                # 字符串、颜色等
│   └── navigation/            # 导航图
└── AndroidManifest.xml
```

## 5. 关键技术点

### 5.1 音频播放
```kotlin
// 使用SoundPool播放短音频（推荐）
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

// 或使用MediaPlayer播放完整音频
fun playAudio(audioResId: Int) {
    MediaPlayer.create(context, audioResId).apply {
        start()
        setOnCompletionListener { release() }
    }
}
```

### 5.2 本地存储
```kotlin
// 使用DataStore（推荐）
class ProgressRepository(private val dataStore: DataStore<Preferences>) {
    suspend fun saveProgress(progress: Progress) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PROGRESS] = 
                Gson().toJson(progress)
        }
    }
    
    suspend fun getProgress(): Progress? {
        val json = dataStore.data.first()[PreferencesKeys.PROGRESS]
        return Gson().fromJson(json, Progress::class.java)
    }
}

// 或使用SharedPreferences（简单场景）
fun saveProgress(progress: Progress) {
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    prefs.edit().apply {
        putString("progress", Gson().toJson(progress))
        apply()
    }
}
```

### 5.3 UI设计
- **Jetpack Compose**: 使用Column、Row、Box等布局组件
- **传统View**: 使用ConstraintLayout、LinearLayout等
- **Material Design**: 遵循Material Design规范
- **响应式**: 适配不同屏幕尺寸（手机、平板）

## 6. 第三方依赖

### 必需依赖
- Android SDK
- AndroidX库（Jetpack组件）
- Kotlin标准库

### 可选依赖
- **Gson** - JSON序列化/反序列化
- **Coroutines** - 异步处理（已包含在Jetpack中）
- **Hilt / Koin** - 依赖注入（如需要）
- **Coil / Glide** - 图片加载（如需要）

## 7. 性能优化

- **内存管理**: 及时释放MediaPlayer、SoundPool等资源
- **异步处理**: 使用Coroutines处理耗时操作
- **图片优化**: 压缩音频和图片资源
- **懒加载**: 按需加载数据
- **RecyclerView优化**: 如使用传统View，优化列表性能
- **Compose性能**: 使用remember、derivedStateOf等优化重组

## 8. 安全考虑

- **输入验证**: 验证用户输入，防止崩溃
- **数据加密**: 敏感数据加密存储（如需要）
- **权限管理**: 合理申请权限，仅申请必要权限
- **ProGuard**: 发布时启用代码混淆

## 9. 技术决策记录

### 决策1: 选择Android原生应用
**时间**: 项目启动时  
**决策**: 使用Kotlin + Jetpack Compose开发Android原生应用  
**原因**: 
- 原生性能好，用户体验佳
- 可直接使用系统API（音频、存储等）
- 适合移动端交互
- 可发布到Google Play Store

### 决策2: 使用Kotlin而非Java
**时间**: 技术选型阶段  
**决策**: 使用Kotlin作为开发语言  
**原因**:
- 现代语言，语法简洁
- 空安全特性，减少崩溃
- 与Java完全互操作
- Google官方推荐

### 决策3: 使用Jetpack Compose
**时间**: UI框架选择阶段  
**决策**: 使用Jetpack Compose构建UI（备选传统View）  
**原因**:
- 声明式UI，开发效率高
- 代码更简洁，易于维护
- Google未来方向
- 如团队不熟悉，可使用传统View系统

### 决策4: 使用DataStore/SharedPreferences而非Room
**时间**: 数据存储设计阶段  
**决策**: 使用DataStore或SharedPreferences存储学习进度  
**原因**:
- MVP阶段数据结构简单
- 无需复杂查询，键值对存储足够
- 性能好，响应快
- 后续如需要复杂数据可升级为Room

### 决策5: 使用SoundPool播放音频
**时间**: 音频功能设计阶段  
**决策**: 使用SoundPool播放拼音发音  
**原因**:
- 适合播放短音频文件
- 性能好，延迟低
- 可同时播放多个音频
- 资源占用小

### 决策记录模板
记录重要技术决策时使用以下格式：

```
### 决策N: [决策标题]
**时间**: [决策时间]  
**决策**: [具体决策内容]  
**原因**: 
- [原因1]
- [原因2]
**影响**: [对项目的影响]
**后续**: [如需调整的计划]
```

