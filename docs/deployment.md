# 部署文档

## 1. 部署概述

本文档描述拼音学习Android应用的构建和发布流程，包括APK构建、签名配置和Google Play发布。

## 0. MVP阶段部署（当前状态）

### ✅ 已完成
- **APK已生成**: `app/build/outputs/apk/debug/app-debug.apk`
- **应用可正常安装和运行**
- **功能测试通过**

### 当前APK信息
- **文件位置**: `D:\ai-try\AndroidApp\PinyinLearning\app\build\outputs\apk\debug\app-debug.apk`
- **构建类型**: Debug
- **签名**: 调试签名
- **状态**: ✅ 可用，可直接安装测试

### 安装方式
1. 将APK文件传输到Android设备
2. 在设备上打开APK文件
3. 允许"未知来源"安装（如需要）
4. 完成安装

### 注意事项
- Debug APK可以正常使用，适合MVP阶段
- 如需正式发布，需要生成签名的Release版本
- 当前APK未优化，体积可能较大

## 2. 构建类型

### 2.1 Debug版本
- 用于开发和测试
- 未签名或使用调试签名
- 包含调试信息
- 可通过USB直接安装

### 2.2 Release版本
- 用于生产环境
- 需要签名
- 代码混淆（ProGuard）
- 优化后的APK

## 3. 构建前准备

### 3.1 代码检查

```bash
# 运行测试
./gradlew test

# 代码检查
./gradlew lint

# 检查依赖
./gradlew dependencies
```

### 3.2 版本号配置

在 `app/build.gradle.kts` 中配置：

```kotlin
android {
    defaultConfig {
        versionCode = 1        // 内部版本号，每次发布递增
        versionName = "1.0.0"  // 用户可见版本号
    }
}
```

### 3.3 构建配置优化

```kotlin
android {
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true      // 启用代码混淆
            isShrinkResources = true    // 移除未使用资源
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
```

## 4. 签名配置

### 4.1 生成密钥库

```bash
# 使用keytool生成密钥库
keytool -genkey -v -keystore pinyin-learning.jks \
    -keyalg RSA -keysize 2048 -validity 10000 \
    -alias pinyin-learning-key
```

**重要提示**：
- 妥善保管密钥库文件和密码
- 丢失密钥库将无法更新应用
- 建议备份到安全位置

### 4.2 配置签名

在 `app/build.gradle.kts` 中配置：

```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("../keystore/pinyin-learning.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = "pinyin-learning-key"
            keyPassword = System.getenv("KEY_PASSWORD")
        }
    }
    
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

### 4.3 安全存储密码（推荐）

创建 `keystore.properties` 文件（不提交到Git）：

```properties
storePassword=your_store_password
keyPassword=your_key_password
keyAlias=pinyin-learning-key
storeFile=../keystore/pinyin-learning.jks
```

在 `build.gradle.kts` 中读取：

```kotlin
val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }
}
```

## 5. 构建APK

### 5.1 构建Debug APK

```bash
# 构建Debug版本
./gradlew assembleDebug

# APK位置
# app/build/outputs/apk/debug/app-debug.apk
```

### 5.2 构建Release APK

```bash
# 构建Release版本
./gradlew assembleRelease

# APK位置
# app/build/outputs/apk/release/app-release.apk
```

### 5.3 构建AAB（Android App Bundle）

Google Play推荐使用AAB格式：

```bash
# 构建Release AAB
./gradlew bundleRelease

# AAB位置
# app/build/outputs/bundle/release/app-release.aab
```

## 6. 安装测试

### 6.1 通过USB安装

```bash
# 启用USB调试后
adb install app/build/outputs/apk/release/app-release.apk
```

### 6.2 通过Android Studio安装

1. 连接设备或启动模拟器
2. Run → Edit Configurations
3. 选择APK，点击Run

## 7. Google Play发布

### 7.1 准备工作

1. 注册Google Play开发者账号
   - 费用：一次性$25
   - 地址：https://play.google.com/console

2. 准备应用信息
   - 应用名称
   - 简短描述（80字符）
   - 完整描述（4000字符）
   - 应用图标（512x512）
   - 功能截图（至少2张）

### 7.2 创建应用

1. 登录Google Play Console
2. 创建新应用
3. 填写基本信息：
   - 应用名称
   - 默认语言
   - 应用类型
   - 免费/付费

### 7.3 上传AAB

1. 进入"发布" → "生产环境"
2. 创建新版本
3. 上传AAB文件（app-release.aab）
4. 填写版本说明

### 7.4 填写商店信息

- **应用图标**: 512x512 PNG
- **功能截图**: 至少2张，最多8张
- **简短描述**: 80字符
- **完整描述**: 4000字符
- **分类**: 教育
- **内容分级**: 完成问卷

### 7.5 定价和分发

- 选择国家/地区
- 设置价格（如免费）
- 选择内容分级

### 7.6 提交审核

1. 检查所有必填项
2. 提交审核
3. 等待审核（通常1-3天）

## 8. 内部测试

### 8.1 创建内部测试轨道

1. 进入"测试" → "内部测试"
2. 创建新版本
3. 上传APK/AAB
4. 添加测试人员邮箱

### 8.2 分享测试链接

测试人员可通过链接安装测试版本。

## 9. 版本更新

### 9.1 更新版本号

```kotlin
android {
    defaultConfig {
        versionCode = 2        // 递增
        versionName = "1.0.1"  // 更新版本号
    }
}
```

### 9.2 构建新版本

```bash
./gradlew bundleRelease
```

### 9.3 上传新版本

1. 在Google Play Console创建新版本
2. 上传新的AAB
3. 填写版本说明
4. 提交审核

## 10. ProGuard配置

### 10.1 基本配置

在 `app/proguard-rules.pro` 中：

```proguard
# 保留数据类
-keep class com.example.pinyinlearning.data.model.** { *; }

# 保留Parcelable
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Gson序列化类
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
```

### 10.2 测试混淆后APK

```bash
# 构建Release版本
./gradlew assembleRelease

# 安装测试
adb install app/build/outputs/apk/release/app-release.apk

# 测试所有功能确保正常
```

## 11. 性能优化

### 11.1 APK大小优化

- 启用资源压缩：`isShrinkResources = true`
- 使用WebP图片格式
- 移除未使用的资源
- 启用代码混淆

### 11.2 构建时间优化

```kotlin
// gradle.properties
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.configureondemand=true
```

## 12. 部署检查清单

### 构建前
- [ ] 代码测试通过
- [ ] 版本号已更新
- [ ] 签名配置正确
- [ ] ProGuard规则配置

### 构建后
- [ ] APK/AAB构建成功
- [ ] 安装测试通过
- [ ] 功能测试通过
- [ ] 性能测试通过

### 发布前
- [ ] 应用信息完整
- [ ] 截图和图标准备
- [ ] 隐私政策（如需要）
- [ ] 内容分级完成

### 发布后
- [ ] 应用可正常下载
- [ ] 功能正常运行
- [ ] 监控崩溃报告
- [ ] 收集用户反馈

## 13. 常见问题

### Q: 构建失败，提示签名错误？
A: 检查密钥库路径和密码是否正确，确保keystore.properties文件存在

### Q: APK安装失败？
A: 检查设备是否允许安装未知来源应用，检查最低SDK版本

### Q: Google Play审核被拒？
A: 查看拒绝原因，常见问题：隐私政策缺失、内容分级不完整、权限说明不清

### Q: 版本更新后用户无法更新？
A: 确保versionCode已递增，签名密钥一致

## 14. 持续集成（CI/CD）

### GitHub Actions示例

创建 `.github/workflows/build.yml`：

```yaml
name: Build and Release

on:
  push:
    tags:
      - 'v*'

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      
      - name: Grant execute permission for gradlew
        run: chmod +x gradlew
      
      - name: Build Release AAB
        run: ./gradlew bundleRelease
        env:
          KEYSTORE_PASSWORD: ${{ secrets.KEYSTORE_PASSWORD }}
          KEY_PASSWORD: ${{ secrets.KEY_PASSWORD }}
      
      - name: Upload AAB
        uses: actions/upload-artifact@v3
        with:
          name: app-release
          path: app/build/outputs/bundle/release/app-release.aab
```

## 15. 安全建议

1. **密钥库安全**
   - 不要提交密钥库到Git
   - 使用环境变量存储密码
   - 备份密钥库到安全位置

2. **代码安全**
   - 启用ProGuard混淆
   - 移除调试信息
   - 检查敏感信息泄露

3. **权限管理**
   - 仅申请必要权限
   - 清晰说明权限用途
   - 遵循最小权限原则
