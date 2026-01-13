# 拼音学习 Android 应用 - 简历项目描述

## 📋 项目概述

**项目名称**: 拼音学习 Android 客户端  
**项目类型**: 教育类移动应用（MVP）  
**开发周期**: 4天  
**项目状态**: ✅ 已完成并上线  
**GitHub**: https://github.com/lmpei/pinyin-learning  
**下载链接**: https://github.com/lmpei/pinyin-learning/releases

---

## 🎯 项目简介

一款帮助用户学习拼音的 Android 原生应用，采用现代 Android 开发技术栈，实现了拼音展示、发音播放、互动练习和学习进度跟踪等核心功能。项目从零开始，完成了需求分析、架构设计、开发实现到部署上线的完整流程。

---

## 💻 技术栈

### 核心技术
- **开发语言**: Kotlin
- **UI 框架**: Jetpack Compose（声明式 UI）
- **架构模式**: MVVM（Model-View-ViewModel）
- **导航**: Navigation Compose
- **异步处理**: Kotlin Coroutines
- **数据存储**: SharedPreferences（本地持久化）
- **音频播放**: TextToSpeech (TTS)

### 开发工具
- **IDE**: Android Studio
- **构建工具**: Gradle (Kotlin DSL)
- **版本控制**: Git
- **CI/CD**: GitHub Actions（自动化构建和发布）

### 技术亮点
- ✅ 采用 Jetpack Compose 构建现代化 UI
- ✅ MVVM 架构，代码结构清晰，易于维护
- ✅ 使用 Kotlin Coroutines 处理异步操作
- ✅ 实现了完整的 CI/CD 流程（GitHub Actions）
- ✅ 支持 Release APK 签名和自动化发布

---

## 🚀 核心功能

### 1. 拼音展示模块
- 展示 23 个声母、24 个韵母和完整拼音
- 支持分类筛选（声母/韵母/完整拼音）
- 搜索功能
- 网格布局展示

### 2. 发音功能
- 点击拼音播放标准发音
- 使用 Android TextToSpeech (TTS) 实现
- 支持中文语音播放
- 自动标记已学习拼音

### 3. 互动练习
- 选择题：听音选拼音
- 拼写题：看字选拼音
- 即时反馈（正确/错误提示）
- 得分统计

### 4. 学习进度
- 记录已学习拼音数量
- 统计练习次数和正确率
- 本地数据持久化存储

---

## 🏗️ 项目架构

### 架构设计
```
┌─────────────────┐
│   UI Layer      │  (Compose Screens)
│  (View)         │
└────────┬────────┘
         │
┌────────▼────────┐
│  ViewModel      │  (业务逻辑)
│  (Logic)        │
└────────┬────────┘
         │
┌────────▼────────┐
│  Repository     │  (数据仓库)
│  (Data)         │
└────────┬────────┘
         │
┌────────▼────────┐
│  DataSource     │  (数据源)
│  (Storage)      │
└─────────────────┘
```

### 模块划分
- **数据层**: Model、Repository、DataSource
- **业务层**: ViewModel、Service
- **UI层**: Compose Screens、Navigation
- **工具层**: 依赖注入、主题配置

---

## 📊 项目成果

### 技术指标
- ✅ 支持 Android 7.0+ (API 24+)
- ✅ 应用大小：约 6.6 MB
- ✅ 页面加载时间 < 2 秒
- ✅ 发音播放延迟 < 500ms

### 功能完成度
- ✅ 拼音展示功能（100%）
- ✅ 发音播放功能（100%）
- ✅ 互动练习功能（100%）
- ✅ 学习进度跟踪（100%）

### 代码质量
- ✅ 遵循 MVVM 架构模式
- ✅ 代码结构清晰，模块化设计
- ✅ 完整的项目文档（10+ 文档）
- ✅ 代码已提交 GitHub 并配置 CI/CD

---

## 🛠️ 个人贡献

### 开发工作
1. **需求分析与架构设计**
   - 完成需求文档编写
   - 设计 MVVM 架构
   - 制定技术选型方案

2. **核心功能开发**
   - 实现拼音数据模型和 Repository 层
   - 开发 Compose UI 界面（4 个主要页面）
   - 实现 TTS 音频播放服务
   - 完成学习进度本地存储功能

3. **项目工程化**
   - 配置 Gradle 构建脚本
   - 实现 Release APK 签名配置
   - 搭建 GitHub Actions CI/CD 流程
   - 配置自动化构建和发布

4. **文档编写**
   - 编写完整项目文档（架构、开发、部署等）
   - 编写 README 和使用指南
   - 编写 CI/CD 配置文档

---

## 📝 简历描述（精简版）

### 版本一：技术导向
**拼音学习 Android 应用** | 独立开发 | 2024
- 使用 Kotlin + Jetpack Compose 开发的教育类 Android 应用
- 采用 MVVM 架构，实现拼音展示、发音播放、互动练习等功能
- 使用 Kotlin Coroutines 处理异步操作，SharedPreferences 实现数据持久化
- 配置 GitHub Actions 实现 CI/CD，支持自动化构建和 Release 发布
- 项目已上线，可在 GitHub Releases 下载 APK

### 版本二：功能导向
**拼音学习 Android 应用** | 独立开发 | 2024
- 开发了一款帮助用户学习拼音的教育类移动应用
- 实现了拼音展示、TTS 发音播放、互动练习和学习进度跟踪等核心功能
- 采用 Jetpack Compose 构建现代化 UI，MVVM 架构保证代码可维护性
- 完成了从需求分析、架构设计到开发部署的完整流程
- 配置自动化 CI/CD 流程，实现一键构建和发布

### 版本三：成果导向
**拼音学习 Android 应用** | 独立开发 | 2024
- 4 天完成 MVP 版本开发，实现核心功能并成功上线
- 使用 Kotlin + Jetpack Compose 技术栈，采用 MVVM 架构模式
- 实现拼音学习、发音播放、互动练习等功能，支持本地数据持久化
- 搭建 GitHub Actions CI/CD 流程，实现自动化构建和发布
- 编写完整项目文档，代码已开源至 GitHub

---

## 🎓 技术亮点（面试重点）

### 1. 现代 Android 开发技术
- ✅ Jetpack Compose 声明式 UI
- ✅ MVVM 架构模式
- ✅ Kotlin Coroutines 异步编程
- ✅ Navigation Compose 导航

### 2. 工程化实践
- ✅ GitHub Actions CI/CD
- ✅ Release APK 签名配置
- ✅ 自动化构建和发布流程
- ✅ 版本管理和 Git 工作流

### 3. 项目经验
- ✅ 从零到一的完整开发流程
- ✅ 需求分析和技术选型
- ✅ 架构设计和模块划分
- ✅ 文档编写和知识沉淀

---

## 📚 相关文档

- [项目 README](./README.md)
- [技术架构文档](./docs/architecture.md)
- [项目总结](./docs/project-summary.md)
- [部署文档](./docs/deployment.md)
- [GitHub Releases 发布指南](./docs/github-releases.md)

---

## 🔗 项目链接

- **GitHub 仓库**: https://github.com/lmpei/pinyin-learning
- **Releases 下载**: https://github.com/lmpei/pinyin-learning/releases
- **最新版本**: v1.0

---

## 💡 面试准备要点

### 可能的问题
1. **为什么选择 Jetpack Compose？**
   - 声明式 UI，开发效率高
   - Google 推荐的未来方向
   - 代码更简洁，易于维护

2. **为什么选择 MVVM 架构？**
   - 清晰的职责分离
   - 便于测试和维护
   - 符合 Android 官方推荐

3. **CI/CD 流程是如何设计的？**
   - 使用 GitHub Actions
   - 自动构建 Release APK
   - 自动创建 GitHub Release
   - 支持手动触发和 tag 触发

4. **遇到的技术难点？**
   - 密钥库路径在 CI 环境中的处理
   - Release 创建时的权限配置
   - 版本号提取和 tag 管理

---

**最后更新**: 2024年

