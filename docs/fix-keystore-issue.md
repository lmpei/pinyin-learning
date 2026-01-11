# 修复密钥库问题

## 问题
错误：`Tag number over 30 is not supported`

这通常是因为密钥库文件格式不兼容。

## 解决方案：重新生成密钥库

### 步骤 1: 删除旧的密钥库（如果存在）

删除或重命名：
- `keystore/pinyin-learning.jks`

### 步骤 2: 重新生成密钥库

在项目根目录（`PinyinLearning`）运行：

**Windows (CMD):**
```cmd
keytool -genkeypair -v -storetype PKCS12 -keystore keystore/pinyin-learning.jks -alias pinyin-learning-key -keyalg RSA -keysize 2048 -validity 10000 -storepass pinyin -keypass pinyin -dname "CN=Pinyin Learning, OU=Development, O=Example, L=City, ST=State, C=CN"
```

**Windows (PowerShell):**
```powershell
keytool -genkeypair -v -storetype PKCS12 -keystore keystore/pinyin-learning.jks -alias pinyin-learning-key -keyalg RSA -keysize 2048 -validity 10000 -storepass pinyin -keypass pinyin -dname "CN=Pinyin Learning, OU=Development, O=Example, L=City, ST=State, C=CN"
```

**Mac/Linux:**
```bash
keytool -genkeypair -v -storetype PKCS12 -keystore keystore/pinyin-learning.jks -alias pinyin-learning-key -keyalg RSA -keysize 2048 -validity 10000 -storepass pinyin -keypass pinyin -dname "CN=Pinyin Learning, OU=Development, O=Example, L=City, ST=State, C=CN"
```

### 步骤 3: 验证密钥库

```bash
keytool -list -v -keystore keystore/pinyin-learning.jks -storepass pinyin
```

### 步骤 4: 重新构建

```bash
.\gradlew clean assembleRelease
```

## 使用 Android Studio 重新创建（推荐）

如果命令行有问题，使用 Android Studio：

1. `Build` → `Generate Signed Bundle / APK`
2. 选择 `APK`
3. 点击 `Create new...`
4. 填写信息：
   - Key store path: `keystore/pinyin-learning.jks`
   - Password: `pinyin`
   - Key alias: `pinyin-learning-key`
   - Password: `pinyin`
   - Validity: `10000`
   - 填写证书信息
5. 点击 `OK`

