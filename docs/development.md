# 开发指南

## 1. 开发环境搭建

### 1.1 前置要求
- Android Studio（最新稳定版）
- JDK 17+
- Android SDK（API Level 24+）
- Android设备或模拟器
- Git（版本控制）

### 1.2 Android Studio安装

1. 下载Android Studio：https://developer.android.com/studio
2. 运行安装程序，选择标准安装
3. 首次启动时：
   - 下载Android SDK
   - 下载构建工具
   - 配置SDK路径

### 1.3 项目初始化

#### 创建新项目
1. 打开Android Studio
2. File → New → New Project
3. 选择模板：**Empty Activity**
4. 配置：
   - Name: `PinyinLearning`
   - Package: `com.example.pinyinlearning`
   - Language: **Kotlin**
   - Minimum SDK: **API 24**
   - Build configuration: Gradle (Kotlin)

#### 项目结构
```
PinyinLearning/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/pinyinlearning/
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle.kts
├── build.gradle.kts
└── settings.gradle.kts
```

### 1.4 推荐依赖配置

在 `app/build.gradle.kts` 中添加：

```kotlin
dependencies {
    // Compose UI（如使用）
    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    
    // 或传统View系统
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    
    // 架构组件
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    
    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("androidx.navigation:navigation-compose:2.7.5") // Compose导航
    
    // 数据存储
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    
    // JSON处理
    implementation("com.google.code.gson:gson:2.10.1")
    
    // 协程
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    
    // 测试
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
```

## 2. 开发规范

### 2.1 代码规范

#### 命名规范
- **类名**: PascalCase（如 `PinyinCard.kt`）
- **函数/变量**: camelCase（如 `playAudio`）
- **常量**: UPPER_SNAKE_CASE（如 `STORAGE_KEY`）
- **包名**: 小写，点分隔（如 `com.example.pinyinlearning`）
- **资源文件**: snake_case（如 `pinyin_card.xml`）

#### 代码风格
```kotlin
// ✅ 好的示例
data class Pinyin(
    val id: String,
    val text: String,
    val tone: Int
)

class AudioService(private val context: Context) {
    fun playPinyin(audioResId: Int) {
        // 实现
    }
}

// ❌ 避免
class audioService {
    fun play(audioResId: Int) {
        // 实现
    }
}
```

### 2.2 架构规范

#### MVVM架构
```
UI Layer (Activity/Fragment/Compose)
    ↓
ViewModel (业务逻辑)
    ↓
Repository (数据管理)
    ↓
DataSource (本地/远程数据)
```

#### 组件规范

**Activity示例**:
```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observeViewModel()
    }
    
    private fun observeViewModel() {
        viewModel.pinyins.observe(this) { pinyins ->
            // 更新UI
        }
    }
}
```

**ViewModel示例**:
```kotlin
class PinyinViewModel : ViewModel() {
    private val repository = PinyinRepository()
    
    private val _pinyins = MutableLiveData<List<Pinyin>>()
    val pinyins: LiveData<List<Pinyin>> = _pinyins
    
    fun loadPinyins() {
        viewModelScope.launch {
            _pinyins.value = repository.getAllPinyins()
        }
    }
}
```

**Compose示例**:
```kotlin
@Composable
fun PinyinScreen(
    viewModel: PinyinViewModel = viewModel()
) {
    val pinyins by viewModel.pinyins.observeAsState(emptyList())
    
    LazyColumn {
        items(pinyins) { pinyin ->
            PinyinCard(pinyin = pinyin)
        }
    }
}
```

### 2.3 Git工作流

#### 分支策略
```
main          # 主分支（生产环境）
├── develop   # 开发分支
└── feature/* # 功能分支
```

#### 提交规范
```
feat: 添加拼音卡片组件
fix: 修复音频播放问题
docs: 更新开发文档
style: 调整代码格式
refactor: 重构进度管理模块
test: 添加单元测试
chore: 更新依赖版本
```

## 3. 开发流程

### 3.1 功能开发步骤

1. **创建功能分支**
   ```bash
   git checkout -b feature/pinyin-display
   ```

2. **开发功能**
   - 创建数据模型
   - 实现ViewModel
   - 创建UI组件
   - 添加样式

3. **自测**
   - 功能测试
   - 边界情况测试
   - 不同设备测试

4. **提交代码**
   ```bash
   git add .
   git commit -m "feat: 添加拼音展示功能"
   git push origin feature/pinyin-display
   ```

5. **合并到主分支**
   - 创建Pull Request
   - Code Review
   - 合并代码

### 3.2 开发检查清单

#### 功能开发前
- [ ] 理解需求
- [ ] 设计组件结构
- [ ] 确定数据流

#### 功能开发中
- [ ] 遵循代码规范
- [ ] 添加必要注释
- [ ] 处理边界情况
- [ ] 考虑错误处理
- [ ] 处理生命周期

#### 功能开发后
- [ ] 功能测试通过
- [ ] 代码自检
- [ ] 更新文档（如需要）

## 4. 调试技巧

### 4.1 Android Studio调试
- 使用断点调试
- 查看Logcat日志
- 使用Layout Inspector（View系统）
- 使用Compose Preview（Compose）

### 4.2 常见问题

#### 音频播放问题
```kotlin
// 确保在UI线程中调用
lifecycleScope.launch {
    audioService.playPinyin(audioResId)
}

// 检查资源ID是否正确
if (audioResId != 0) {
    soundPool.play(sampleId, 1f, 1f, 1, 0, 1f)
} else {
    Log.e("AudioService", "Audio resource not found")
}
```

#### 内存泄漏
```kotlin
// 及时释放资源
override fun onDestroy() {
    super.onDestroy()
    soundPool.release()
    mediaPlayer?.release()
}
```

#### 数据存储问题
```kotlin
// DataStore是异步的，使用协程
viewModelScope.launch {
    repository.saveProgress(progress)
}

// 读取数据
viewModelScope.launch {
    val progress = repository.getProgress().first()
    _progress.value = progress
}
```

## 5. 测试策略

### 5.1 单元测试
```kotlin
// PinyinRepositoryTest.kt
class PinyinRepositoryTest {
    @Test
    fun `getAllPinyins returns correct list`() {
        val repository = PinyinRepository()
        val pinyins = repository.getAllPinyins()
        
        assertTrue(pinyins.isNotEmpty())
        assertEquals("ma1", pinyins[0].id)
    }
}
```

### 5.2 UI测试
```kotlin
// MainActivityTest.kt
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun testPinyinDisplay() {
        // Espresso测试
        onView(withId(R.id.pinyinList))
            .check(matches(isDisplayed()))
    }
}
```

### 5.3 手动测试清单
- [ ] 所有拼音都能正常显示
- [ ] 音频播放正常
- [ ] 练习功能正常
- [ ] 进度保存正常
- [ ] 不同屏幕尺寸适配正常

## 6. 性能优化

### 6.1 代码优化
- 使用`remember`、`derivedStateOf`优化Compose重组
- 使用`RecyclerView`的`ViewHolder`复用（传统View）
- 避免在主线程执行耗时操作
- 使用协程处理异步任务

### 6.2 资源优化
- 压缩音频文件
- 使用WebP格式图片
- 按需加载资源
- 启用ProGuard代码混淆

## 7. 构建配置

### 7.1 构建变体
```kotlin
// build.gradle.kts
android {
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
```

### 7.2 签名配置
```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("keystore.jks")
            storePassword = "password"
            keyAlias = "key"
            keyPassword = "password"
        }
    }
}
```

## 8. 开发工具推荐

- **IDE**: Android Studio
- **插件**: 
  - Kotlin
  - Git Integration
  - ADB Idea
- **工具**: 
  - Logcat
  - Layout Inspector
  - Profiler

## 9. 常见命令

```bash
# 构建Debug版本
./gradlew assembleDebug

# 构建Release版本
./gradlew assembleRelease

# 运行测试
./gradlew test

# 清理构建
./gradlew clean

# 安装到设备
./gradlew installDebug
```

## 10. 最佳实践

1. **使用Kotlin协程**处理异步操作
2. **遵循Material Design**设计规范
3. **处理配置变更**（屏幕旋转等）
4. **合理使用生命周期**感知组件
5. **及时释放资源**避免内存泄漏
6. **使用类型安全**的导航和存储
