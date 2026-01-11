# 快速发布指南（推荐）

如果你已经通过 Android Studio 生成了 Release APK，**直接手动上传最快**（只需 1-2 分钟）！

## 🚀 最快方式：手动上传（1-2 分钟）

### 步骤

1. **本地构建 APK**（如果还没有）
   ```bash
   .\gradlew assembleRelease
   ```
   APK 位置：`app/build/outputs/apk/release/app-release.apk`

2. **上传到 GitHub Releases**
   - 访问：https://github.com/lmpei/pinyin-learning/releases
   - 点击 `Create a new release`
   - **Tag**: 输入 `v1.0.1`（或其他版本号）
   - **Title**: `版本 1.0.1`
   - **Description**: 填写更新说明
   - **上传文件**: 拖拽 `app-release.apk` 文件
   - 点击 `Publish release`

**完成！** 用户现在可以下载了。

---

## ⚙️ 自动化方式（3-5 分钟）

如果你想要自动化构建和发布：

1. 进入 GitHub Actions：https://github.com/lmpei/pinyin-learning/actions
2. 选择 `Build and Release APK` 工作流
3. 点击 `Run workflow` → `Run workflow`
4. 等待 3-5 分钟完成

**适用场景**：
- 需要确保构建环境一致
- 团队协作，多人发布
- 需要自动化测试

---

## 💡 建议

- **日常发布**：使用手动上传（快）
- **重要版本**：使用自动化构建（确保一致性）
- **频繁更新**：可以两种方式结合使用

