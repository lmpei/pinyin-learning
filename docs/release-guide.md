# 正式发布指南

## 快速开始

本指南将帮助你生成签名的Release APK，用于正式发布。

## 步骤 1: 生成签名密钥库

### 方法一：使用 Android Studio（推荐）

1. 在 Android Studio 中：
   - 点击 `Build` → `Generate Signed Bundle / APK`
   - 选择 `APK`
   - 点击 `Create new...` 创建新密钥库

2. 填写密钥库信息：
   - **Key store path**: 选择保存位置（建议：项目根目录下的 `keystore/` 文件夹）
   - **Password**: 设置密钥库密码（请记住！）
   - **Key alias**: `pinyin-learning-key`
   - **Password**: 设置密钥密码（可以与密钥库密码相同）
   - **Validity**: `10000`（约27年）
   - **Certificate**: 填写你的信息（姓名、组织等）

3. 点击 `OK` 创建密钥库

### 方法二：使用命令行

在项目根目录下运行：

```bash
# Windows
keytool -genkey -v -keystore keystore/pinyin-learning.jks -keyalg RSA -keysize 2048 -validity 10000 -alias pinyin-learning-key

# Mac/Linux
keytool -genkey -v -keystore keystore/pinyin-learning.jks -keyalg RSA -keysize 2048 -validity 10000 -alias pinyin-learning-key
```

按提示填写信息：
- 密钥库密码
- 密钥密码
- 姓名、组织等信息

## 步骤 2: 配置签名信息

1. 在项目根目录创建 `keystore.properties` 文件：

```properties
storePassword=你的密钥库密码
keyPassword=你的密钥密码
keyAlias=pinyin-learning-key
storeFile=keystore/pinyin-learning.jks
```

2. **重要**：确保 `keystore.properties` 已添加到 `.gitignore`（已自动配置）

## 步骤 3: 构建 Release APK

### 方法一：使用 Android Studio

1. 点击 `Build` → `Generate Signed Bundle / APK`
2. 选择 `APK`
3. 选择已创建的密钥库文件
4. 输入密码
5. 选择 `release` 构建变体
6. 点击 `Finish`
7. 等待构建完成

### 方法二：使用 Gradle

在 Android Studio 的 Terminal 中运行：

```bash
# Windows
.\gradlew assembleRelease

# Mac/Linux
./gradlew assembleRelease
```

## 步骤 4: 找到 Release APK

构建完成后，APK 文件位置：
```
app/build/outputs/apk/release/app-release.apk
```

## 步骤 5: 测试 Release APK

1. 卸载设备上的 Debug 版本（如已安装）
2. 安装 Release APK
3. 测试所有功能确保正常

## 重要提示

### 密钥库安全

⚠️ **非常重要**：
- **妥善保管密钥库文件**（`.jks` 文件）
- **记住密钥库密码和密钥密码**
- **备份密钥库到安全位置**
- **不要提交密钥库到Git**（已配置.gitignore）
- **丢失密钥库将无法更新应用！**

### 版本号管理

每次发布新版本时，需要更新版本号：

在 `app/build.gradle.kts` 中：
```kotlin
defaultConfig {
    versionCode = 2        // 每次发布递增（1, 2, 3...）
    versionName = "1.0.1"  // 用户可见版本号（如：1.0.1, 1.1.0, 2.0.0）
}
```

### 发布检查清单

构建 Release APK 前：
- [ ] 功能测试通过
- [ ] 版本号已更新
- [ ] 密钥库已创建
- [ ] `keystore.properties` 已配置
- [ ] 密钥库已备份

构建后：
- [ ] Release APK 构建成功
- [ ] 安装测试通过
- [ ] 功能测试通过
- [ ] 性能测试通过

## 后续优化（可选）

### 启用代码混淆

在 `app/build.gradle.kts` 中：
```kotlin
buildTypes {
    release {
        isMinifyEnabled = true  // 改为 true
        // ...
    }
}
```

**注意**：启用后需要测试确保功能正常。

### 资源压缩

```kotlin
buildTypes {
    release {
        isMinifyEnabled = true
        isShrinkResources = true  // 移除未使用的资源
        // ...
    }
}
```

## 发布到应用商店

### Google Play Store

1. 注册 Google Play 开发者账号（$25一次性费用）
2. 创建应用
3. 上传 AAB 文件（推荐）或 APK
4. 填写应用信息
5. 提交审核

### 其他分发方式

- 直接分享 APK 文件
- 通过网盘分发
- 企业内部分发

## 常见问题

### Q: 构建失败，提示找不到密钥库？
A: 检查 `keystore.properties` 文件路径是否正确，确保密钥库文件存在。

### Q: 提示密码错误？
A: 检查 `keystore.properties` 中的密码是否正确。

### Q: 可以跳过签名直接发布吗？
A: 不可以。所有正式发布的APK都必须签名。

### Q: 密钥库丢失了怎么办？
A: 无法恢复。需要创建新的密钥库，但无法更新已发布的应用（需要重新发布为新应用）。

---

**完成这些步骤后，你就有了一个可以正式发布的签名APK！**

