# GitHub Releases 发布指南

本文档说明如何将 APK 发布到 GitHub Releases，让用户能够快速下载。

## 方法一：自动发布（推荐）

使用 GitHub Actions 自动构建和发布 APK。

### 1. 配置 GitHub Secrets

在 GitHub 仓库设置中添加以下 Secrets：

1. 进入仓库：`Settings` → `Secrets and variables` → `Actions`
2. 点击 `New repository secret`，添加以下密钥：

#### 必需配置

- **`KEYSTORE_PASSWORD`**: 密钥库密码
- **`KEY_PASSWORD`**: 密钥密码
- **`KEY_ALIAS`**: 密钥别名（通常是 `pinyin-learning-key`）

#### 密钥库文件配置

将密钥库文件转换为 Base64 编码后添加到 Secrets：

**Windows (PowerShell):**
```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("keystore/pinyin-learning.jks")) | Out-File -Encoding ASCII keystore-base64.txt
```

**Mac/Linux:**
```bash
base64 -i keystore/pinyin-learning.jks -o keystore-base64.txt
```

然后复制 `keystore-base64.txt` 的内容，添加到 Secret：
- **`KEYSTORE_BASE64`**: 密钥库文件的 Base64 编码内容

### 2. 创建 Release

有两种方式触发自动构建：

#### 方式 A：通过 Git Tag（推荐）

```bash
# 1. 确保代码已提交
git add .
git commit -m "Prepare release v1.0.0"
git push

# 2. 创建并推送 tag
git tag v1.0.0
git push origin v1.0.0
```

GitHub Actions 会自动：
- 构建签名的 Release APK
- 创建 GitHub Release
- 上传 APK 文件

#### 方式 B：手动触发

1. 进入 GitHub 仓库
2. 点击 `Actions` 标签
3. 选择 `Build and Release APK` 工作流
4. 点击 `Run workflow`
5. 选择分支，点击 `Run workflow`

### 3. 查看 Release

构建完成后：
1. 进入仓库的 `Releases` 页面
2. 可以看到新创建的 Release
3. APK 文件会自动附加到 Release 中

---

## 方法二：手动发布（简单快速）

如果不想配置自动化，可以手动创建 Release 并上传 APK。

### 1. 本地构建 APK

```bash
# Windows
.\gradlew assembleRelease

# Mac/Linux
./gradlew assembleRelease
```

APK 文件位置：`app/build/outputs/apk/release/app-release.apk`

### 2. 创建 GitHub Release

1. 进入 GitHub 仓库
2. 点击右侧的 `Releases`
3. 点击 `Create a new release`
4. 填写信息：
   - **Tag version**: 输入版本号，如 `v1.0.0`（首次创建会自动创建 tag）
   - **Release title**: 如 `版本 1.0.0` 或 `Version 1.0.0`
   - **Description**: 填写更新说明，例如：
     ```
     ## 版本 1.0.0
     
     ### 新功能
     - 拼音学习功能
     - 发音练习
     - 进度跟踪
     
     ### 修复
     - 修复了若干已知问题
     ```
5. **上传文件**：
   - 点击 `Attach binaries` 或拖拽文件
   - 选择 `app-release.apk` 文件
   - 可以重命名为 `pinyin-learning-v1.0.0.apk`（可选）
6. 选择 `Publish release`（正式发布）或 `Save draft`（保存草稿）

### 3. 在 README 中添加下载链接

在 README.md 中添加：

```markdown
## 📥 下载

[![Latest Release](https://img.shields.io/github/v/release/lmpei/pinyin-learning?label=Download&style=for-the-badge)](https://github.com/lmpei/pinyin-learning/releases/latest)

访问 [Releases 页面](https://github.com/lmpei/pinyin-learning/releases) 下载最新版本。
```

---

## 行业最佳实践

### 1. 版本号规范

遵循 [语义化版本](https://semver.org/lang/zh-CN/)：
- **主版本号**：不兼容的 API 修改
- **次版本号**：向下兼容的功能性新增
- **修订号**：向下兼容的问题修正

示例：`v1.0.0`, `v1.1.0`, `v1.1.1`, `v2.0.0`

### 2. Release 说明模板

```markdown
## 🎉 版本 1.0.0

### ✨ 新功能
- 功能描述 1
- 功能描述 2

### 🐛 修复
- 修复了问题 1
- 修复了问题 2

### 📝 改进
- 优化了性能
- 改进了用户体验

### 📦 安装
1. 下载 APK 文件
2. 在 Android 设备上安装
3. 允许"未知来源"安装（如需要）
```

### 3. 使用 Release Badge

在 README 中添加徽章，让用户快速看到最新版本：

```markdown
[![GitHub release](https://img.shields.io/github/v/release/lmpei/pinyin-learning)](https://github.com/lmpei/pinyin-learning/releases)
[![GitHub downloads](https://img.shields.io/github/downloads/lmpei/pinyin-learning/total)](https://github.com/lmpei/pinyin-learning/releases)
```

### 4. 自动化优势

使用 GitHub Actions 自动化的好处：
- ✅ 每次发布自动构建，减少人为错误
- ✅ 构建环境一致，确保 APK 质量
- ✅ 自动生成 Release 说明
- ✅ 节省手动操作时间

### 5. 安全建议

- ⚠️ **不要**将密钥库文件提交到 Git
- ✅ 使用 GitHub Secrets 存储敏感信息
- ✅ 定期备份密钥库文件
- ✅ 限制 Secrets 的访问权限

---

## 常见问题

### Q: 如何更新版本号？

在 `app/build.gradle.kts` 中更新：

```kotlin
defaultConfig {
    versionCode = 2        // 递增
    versionName = "1.0.1"  // 更新版本号
}
```

### Q: Release 中看不到 APK？

- 检查 GitHub Actions 是否构建成功
- 确认 APK 文件路径正确
- 查看 Actions 日志排查问题

### Q: 如何回退到旧版本？

1. 在 Releases 页面找到旧版本
2. 下载旧版本的 APK
3. 卸载当前版本后安装旧版本

### Q: 可以同时发布多个版本吗？

可以，但建议只保留最新的稳定版本和最新的测试版本。

---

## 参考资源

- [GitHub Releases 文档](https://docs.github.com/en/repositories/releasing-projects-on-github)
- [GitHub Actions 文档](https://docs.github.com/en/actions)
- [语义化版本规范](https://semver.org/lang/zh-CN/)

