# 拼音学习Android客户端 - MVP项目

## 项目概述

这是一个帮助用户学习拼音的Android原生应用，旨在快速完成MVP版本，验证核心功能。

## 快速开始

### 前置要求
- Android Studio（最新稳定版）
- JDK 17+
- Android SDK（API Level 24+）
- Android设备或模拟器

### 安装与运行
```bash
# 克隆项目（如使用Git）
git clone <repository-url>
cd PinyinLearning

# 使用Android Studio打开项目
# File -> Open -> 选择 PinyinLearning 目录

# 运行应用
# 点击 Run 按钮或按 Shift+F10
```

## 项目结构

```
PinyinLearning/
├── README.md              # 项目总览
├── docs/                  # 文档目录
│   ├── quick-start.md     # 快速开始指南
│   ├── project-plan.md    # 项目规划（4天MVP计划）
│   ├── requirements.md    # 需求文档
│   ├── architecture.md    # 技术架构
│   ├── development.md     # 开发指南
│   ├── api.md             # API设计
│   ├── testing.md         # 测试计划
│   └── deployment.md      # 部署文档
├── app/                   # Android应用模块
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/      # Kotlin/Java源代码
│   │   │   ├── res/       # 资源文件
│   │   │   └── AndroidManifest.xml
│   │   └── test/          # 单元测试
│   └── build.gradle.kts   # 模块构建配置
├── build.gradle.kts       # 项目构建配置
└── settings.gradle.kts    # 项目设置
```

## 核心功能

1. **拼音展示** - 显示拼音字母和声调
2. **发音练习** - 点击播放拼音发音
3. **互动学习** - 简单的选择题或拼写练习
4. **进度跟踪** - 记录学习进度

## 开发阶段

- [x] 项目文档体系搭建
- [x] 技术选型确定（Kotlin + Jetpack Compose + MVVM）
- [x] 核心功能开发
- [x] 测试与优化
- [x] 部署上线（APK已生成）

## 📥 下载安装

### 最新版本下载

**推荐方式：从 GitHub Releases 下载**

1. 访问项目的 [Releases 页面](https://github.com/lmpei/pinyin-learning/releases)
2. 找到最新版本
3. 下载 `pinyin-learning-*.apk` 文件
4. 在 Android 设备上安装

> 💡 **提示**：如果 GitHub Releases 中还没有文件，请查看下方的"手动创建 Release"说明。

### 安装步骤

1. 在 Android 设备上打开下载的 APK 文件
2. 如果提示"未知来源"，请在设置中允许安装来自此来源的应用
3. 完成安装后即可使用

## 项目状态

✅ **MVP版本已完成并可用**

- 所有核心功能已实现
- 应用可正常安装和运行
- APK文件已生成：`app/build/outputs/apk/release/app-release.apk`

## 文档导航

- [快速开始](./docs/quick-start.md) - 5分钟快速启动指南
- [项目规划](./docs/project-plan.md) - 4天MVP开发计划
- [需求文档](./docs/requirements.md) - 详细功能需求
- [技术架构](./docs/architecture.md) - 技术选型与架构设计
- [开发指南](./docs/development.md) - 开发流程与规范
- [项目总结](./docs/project-summary.md) - 项目完成总结
- [优化建议](./docs/optimization-suggestions.md) - 后续优化方向
- [API设计](./docs/api.md) - 接口设计文档
- [测试计划](./docs/testing.md) - 测试策略与计划
- [部署文档](./docs/deployment.md) - 部署流程
- [GitHub Releases 发布指南](./docs/github-releases.md) - 如何发布 APK 到 GitHub Releases

## 联系方式

如有问题，请查看相关文档或提交Issue。

