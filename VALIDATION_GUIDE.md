# GitHub Actions 验证指南

## 方法一：手动触发工作流（推荐，快速验证）

1. 访问 GitHub 仓库：https://github.com/lmpei/pinyin-learning
2. 点击顶部的 **`Actions`** 标签
3. 在左侧选择 **`Build and Release APK`** 工作流
4. 点击右侧的 **`Run workflow`** 按钮
5. 选择分支（通常是 `master`）
6. 点击绿色的 **`Run workflow`** 按钮
7. 等待构建完成（通常需要 3-5 分钟）

### 检查构建结果

- ✅ **成功**：会看到绿色的 ✓，并且会自动创建 GitHub Release
- ❌ **失败**：会看到红色的 ✗，点击查看详细错误信息

### 常见问题排查

如果构建失败，检查以下内容：

1. **Secrets 配置错误**
   - 检查 `KEYSTORE_PASSWORD`、`KEY_PASSWORD`、`KEY_ALIAS` 是否正确
   - 检查 `KEYSTORE_BASE64` 是否完整（应该是一行很长的字符串）

2. **密钥库路径问题**
   - 确认 `KEY_ALIAS` 的值是 `pinyin-learning-key`（与 keystore.properties 中的一致）

3. **查看详细日志**
   - 点击失败的构建
   - 展开各个步骤查看详细错误信息

---

## 方法二：通过 Git Tag 触发（真实发布流程）

如果你想测试完整的发布流程（包括自动创建 Release）：

```bash
# 1. 确保所有更改已提交
git status

# 2. 创建并推送 tag
git tag v1.0.0
git push origin v1.0.0
```

这会自动触发构建并创建 GitHub Release。

---

## 验证清单

构建成功后，检查以下内容：

- [ ] GitHub Actions 构建成功（绿色 ✓）
- [ ] 在 Releases 页面可以看到新创建的 Release
- [ ] Release 中包含 APK 文件（`pinyin-learning-v1.0.0.apk`）
- [ ] APK 文件可以下载
- [ ] Release 说明已自动生成

---

## 下一步

验证成功后：
1. 可以在 README 中添加下载链接
2. 可以分享 Release 链接给用户
3. 后续发布只需创建新的 tag 即可

